package com.example.demo.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.MailAudit;
import com.example.demo.entity.TelAudit;
import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.customer.CustomerFile;
import com.example.demo.entity.customer.Person;
import com.example.demo.service.MailAuditService;
import com.example.demo.service.TelAuditService;
import com.example.demo.service.customer.CustomerFileService;
import com.example.demo.service.customer.PersonService;

@Controller
public class PersonController {

	@Autowired
	PersonService personService;

	@Autowired
	CustomerFileService fileService;

	@Autowired
	TelAuditService telService;

	@Autowired
	MailAuditService mailService;


	/**
	 * 個人顧客一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/people")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "person/index::person_contents");
		mav.addObject("title", "個人顧客一覧");
		Page<Person> list = personService.getAll(pageable);
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 個人顧客新規作成
	 * @param person
	 * @param mav
	 * @return
	 */
	@GetMapping("/people/new")
	public ModelAndView personNew(
			@ModelAttribute Person person,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "person/new::person_contents");
		mav.addObject("title", "個人顧客作成");
		return mav;
	}

	@PostMapping("/people/new")
	public ModelAndView create(
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("mailKind") String[] mailKinds,
			@RequestParam("mailAddr") String[] mailAddrs,
			@RequestParam("phoneKind") String[] telKinds,
			@RequestParam("phoneNumber") String[] telNumbers,
			@ModelAttribute("person")
			@Validated Person person,
			BindingResult result,
			ModelAndView mav) throws IOException {

		ModelAndView res = null;

		if(!result.hasErrors()) {

			personService.savePerson(person);

			List<CustomerFile> customerFiles = new ArrayList<CustomerFile>();
			for(MultipartFile file : files) {
				CustomerFile customerFile = new CustomerFile();
				customerFile.setFileName(file.getOriginalFilename());
				customerFile.setFile(file.getBytes());
				customerFile.setCustomer((Customer) person);
				fileService.saveCustomerFile(customerFile);
				customerFiles.add(customerFile);
			}
			person.setCustomerFileList(customerFiles);

			List<MailAudit> mails = new ArrayList<MailAudit>();
			for(int i = 0; i < mailAddrs.length; i++) {
				MailAudit mail = new MailAudit();
				mail.setMailKind(mailKinds[i]);
				mail.setMailAddr(mailAddrs[i]);
				mailService.saveMail(mail);
				mails.add(mail);
			}
			person.setMailList(mails);

			List<TelAudit> tels = new ArrayList<TelAudit>();
			for(int i = 0; i < telNumbers.length; i++) {
				TelAudit tel = new TelAudit();
				tel.setPhoneKind(telKinds[i]);
				tel.setPhoneNumber(telNumbers[i]);
				telService.saveTel(tel);
				tels.add(tel);
			}
			person.setTelephoneList(tels);

			personService.savePerson(person);

			res = new ModelAndView("redirect:/people/" + person.getId());
		} else {
			mav.setViewName("layout");
			mav.addObject("msg", "エラー発生");
			mav.addObject("contents", "person/new::person_contents");
			mav.addObject("title", "個人顧客新規作成");
			res = mav;
		}
		return res;
	}

	/**
	 * 個人顧客詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/people/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		Person person = personService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "person/show::person_contents");
		mav.addObject("person", person);
		mav.addObject("title", "顧客詳細");
		return mav;
	}

	/**
	 * 個人顧客編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/people/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "person/edit::person_contents");
		mav.addObject("title", "個人顧客編集");
		mav.addObject("person", personService.find(id));
		return mav;
	}

	@PostMapping("/people/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@RequestParam(name = "mailKind", required = false) String[] mailKinds,
			@RequestParam(name = "mailAddr", required = false) String[] mailAddrs,
			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
			@ModelAttribute("person")
			@Validated Person person,
			BindingResult result,
			ModelAndView mav
			) {
		if(!result.hasErrors()) {
			Person editperson = personService.find(id);
			editperson.setId(id);
			editperson.setName(person.getName());
			editperson.setKana(person.getKana());
			editperson.setBirthday(person.getBirthday());
			editperson.setMemo(person.getMemo());

			if(!editperson.getMailList().isEmpty()) {
				List<MailAudit> mails = editperson.getMailList();
				for(int i = 0; i < mails.size(); i++) {
					mails.get(i).setMailKind(mailKinds[i]);
					mails.get(i).setMailAddr(mailAddrs[i]);
				}
			}

			if(!editperson.getTelephoneList().isEmpty()) {
				List<TelAudit> tels = editperson.getTelephoneList();
				for(int i = 0; i < tels.size(); i++) {
					tels.get(i).setPhoneKind(phoneKinds[i]);
					tels.get(i).setPhoneNumber(phoneNumbers[i]);
				}
			}

			personService.savePerson(editperson);

		} else {
			mav.setViewName("layout");
			mav.addObject("contents", "person/edit::person_contents");
			mav.addObject("title", "個人顧客編集");
			mav.addObject("person", personService.find(id));
			return mav;
		}

		return new ModelAndView("redirect:/people/{id}");
	}

	@PostMapping("/people/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		personService.delete(id);
		return new ModelAndView("redirect:/people");
	}

}
