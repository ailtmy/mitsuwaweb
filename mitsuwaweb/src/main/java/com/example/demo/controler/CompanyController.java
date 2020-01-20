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
import com.example.demo.entity.customer.Company;
import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.customer.CustomerFile;
import com.example.demo.entity.customer.Daihyo;
import com.example.demo.entity.customer.Person;
import com.example.demo.service.MailAuditService;
import com.example.demo.service.TelAuditService;
import com.example.demo.service.customer.CompanyService;
import com.example.demo.service.customer.CustomerFileService;
import com.example.demo.service.customer.DaihyoService;
import com.example.demo.service.customer.PersonService;

@Controller
public class CompanyController {

	@Autowired
	PersonService personService;

	@Autowired
	CompanyService companyService;

	@Autowired
	CustomerFileService fileService;

	@Autowired
	TelAuditService telService;

	@Autowired
	MailAuditService mailService;

	@Autowired
	DaihyoService daihyoService;

	/**
	 * 法人顧客一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/companies")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "company/index::company_contents");
		mav.addObject("title", "法人顧客一覧");
		Page<Company> list = companyService.getAll(pageable);
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 法人顧客新規作成
	 * @param company
	 * @param mav
	 * @return
	 */
	@GetMapping("/companies/new")
	public ModelAndView companyNew(
			@ModelAttribute Company company,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "company/new::company_contents");
		mav.addObject("title", "法人顧客作成");
		mav.addObject("person", personService.allGet());
		return mav;
	}

	@PostMapping("/companies/new")
	public ModelAndView create(
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("mailKind") String[] mailKinds,
			@RequestParam("mailAddr") String[] mailAddrs,
			@RequestParam("phoneKind") String[] telKinds,
			@RequestParam("phoneNumber") String[] telNumbers,
//			@RequestParam(name = "katagaki", required = false) String katagaki,
//			@RequestParam(name = "daihyosya", defaultValue="0") Integer daihyosya,
			@ModelAttribute("company")
			@Validated Company company,
			BindingResult result,
			ModelAndView mav) throws IOException {

		ModelAndView res = null;

		if(!result.hasErrors()) {
			companyService.saveCompany(company);


			List<CustomerFile> customerFiles = new ArrayList<CustomerFile>();
			for(MultipartFile file : files) {
				CustomerFile customerFile = new CustomerFile();
				customerFile.setFileName(file.getOriginalFilename());
				customerFile.setFile(file.getBytes());
				customerFile.setCustomer((Customer) company);
				fileService.saveCustomerFile(customerFile);
				customerFiles.add(customerFile);
			}
			company.setCustomerFileList(customerFiles);

//			if(daihyosya != 0) {
//				List<Daihyo> daihyos = new ArrayList<Daihyo>();
//				Daihyo daihyo = new Daihyo();
//				daihyo.setKatagaki(katagaki);
//				daihyo.setPerson(personService.find(daihyosya));
//				daihyos.add(daihyo);
//				company.setDaihyosya(daihyos);
//			}

			List<MailAudit> mails = new ArrayList<MailAudit>();
			for(int i = 0; i < mailAddrs.length; i++) {
				MailAudit mail = new MailAudit();
				mail.setMailKind(mailKinds[i]);
				mail.setMailAddr(mailAddrs[i]);
				mailService.saveMail(mail);
				mails.add(mail);
			}
			company.setMailList(mails);

			List<TelAudit> tels = new ArrayList<TelAudit>();
			for(int i = 0; i < telNumbers.length; i++) {
				TelAudit tel = new TelAudit();
				tel.setPhoneKind(telKinds[i]);
				tel.setPhoneNumber(telNumbers[i]);
				telService.saveTel(tel);
				tels.add(tel);
			}
			company.setTelephoneList(tels);

			companyService.saveCompany(company);

			res = new ModelAndView("redirect:/companies/" + company.getId());
		} else {
			mav.setViewName("layout");
			mav.addObject("msg", "エラー発生");
			mav.addObject("contents", "company/new::company_contents");
			mav.addObject("title", "法人顧客新規作成");
			res = mav;
		}
		return res;
	}

	/**
	 * 法人顧客詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/companies/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		Company company = companyService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "company/show::company_contents");
		mav.addObject("company", company);
		mav.addObject("title", "法人顧客詳細");
		return mav;
	}

	/**
	 * 法人顧客編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/companies/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "company/edit::company_contents");
		mav.addObject("title", "法人顧客編集");
		mav.addObject("company", companyService.find(id));
		mav.addObject("daihyos", personService.allGet());
		return mav;
	}


	@PostMapping("/companies/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@RequestParam(name = "mailKind", required = false) String[] mailKinds,
			@RequestParam(name = "mailAddr", required = false) String[] mailAddrs,
			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
			@RequestParam(name = "katagaki", required = false) String[] katagakis,
			@RequestParam(name = "personId", required = false) Person[] persons,
			@ModelAttribute("company")
			@Validated Company company,
			BindingResult result,
			ModelAndView mav
			) {
		if(!result.hasErrors()) {
			Company editcompany = companyService.find(id);
			editcompany.setId(id);
			editcompany.setName(company.getName());
			editcompany.setKana(company.getKana());
			editcompany.setBirthday(company.getBirthday());
			editcompany.setHoujinbango(company.getHoujinbango());
			editcompany.setNextSyogyoToki(company.getNextSyogyoToki());
			editcompany.setMemo(company.getMemo());

			if(!editcompany.getDaihyosya().isEmpty()) {
				List<Daihyo> daihyos = editcompany.getDaihyosya();
				for(int i = 0; i < daihyos.size(); i++) {
					daihyos.get(i).setKatagaki(katagakis[i]);
					daihyos.get(i).setDaitori(persons[i]);
				}
			}

			if(!editcompany.getMailList().isEmpty()) {
				List<MailAudit> mails = editcompany.getMailList();
				for(int i = 0; i < mails.size(); i++) {
					mails.get(i).setMailKind(mailKinds[i]);
					mails.get(i).setMailAddr(mailAddrs[i]);
				}
			}

			if(!editcompany.getTelephoneList().isEmpty()) {
				List<TelAudit> tels = editcompany.getTelephoneList();
				for(int i = 0; i < tels.size(); i++) {
					tels.get(i).setPhoneKind(phoneKinds[i]);
					tels.get(i).setPhoneNumber(phoneNumbers[i]);
				}
			}

			companyService.saveCompany(editcompany);

		} else {
			mav.setViewName("layout");
			mav.addObject("contents", "company/edit::company_contents");
			mav.addObject("title", "法人顧客編集");
			mav.addObject("company", companyService.find(id));
			return mav;
		}

		return new ModelAndView("redirect:/company/{id}");
	}

	/**
	 * 法人顧客削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/companies/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		companyService.delete(id);
		return new ModelAndView("redirect:/companies");
	}

	@GetMapping("/companies/{id}/daihyonew")
	public ModelAndView daihyoNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		Company company = companyService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "company/daihyonew::company_contents");
		mav.addObject("title", "代表者新規作成");
		mav.addObject("company", company);
		mav.addObject("daihyo", personService.allGet());
		return mav;
	}

	@PostMapping("/companies/{id}/daihyonew")
	public ModelAndView daihyocreate(
			@PathVariable Integer id,
			@ModelAttribute Daihyo daihyo,
			ModelAndView mav
			) {
		Company company = companyService.find(id);
		List<Daihyo> daihyos = null;

		if(company.getDaihyosya().isEmpty() || company.getDaihyosya() == null) {
			daihyos = new ArrayList<Daihyo>();
			Daihyo dai = new Daihyo();
			dai.setKatagaki(daihyo.getKatagaki());
			dai.setDaitori(daihyo.getDaitori());
			daihyoService.saveDaihyo(dai);
			daihyos.add(dai);
		} else {
			daihyos = company.getDaihyosya();
			Daihyo dai = new Daihyo();
			dai.setKatagaki(daihyo.getKatagaki());
			dai.setDaitori(daihyo.getDaitori());
			daihyoService.saveDaihyo(dai);
			daihyos.add(dai);
		}

		company.setDaihyosya(daihyos);
		companyService.saveCompany(company);

		return new ModelAndView("redirect:/companies/{id}");

	}
}
