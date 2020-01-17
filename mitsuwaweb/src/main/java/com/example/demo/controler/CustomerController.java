package com.example.demo.controler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.customer.CustomerFile;
import com.example.demo.service.MailAuditService;
import com.example.demo.service.TelAuditService;
import com.example.demo.service.customer.CustomerFileService;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.service.honninkakunin.HonninKakuninService;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	TelAuditService telephoneService;

	@Autowired
	MailAuditService mailaddressService;

	@Autowired
	CustomerFileService fileService;

	@Autowired
	HonninKakuninService honninKakuninService;

	/**
	 * 顧客一覧
	 */
	@GetMapping("/customers")
	public ModelAndView customerIndex(ModelAndView mav,
			@PageableDefault(page=0, size=5)
			Pageable pageable) {
		mav.setViewName("layout");
		mav.addObject("contents", "customer/index::customer_contents");
		mav.addObject("title", "顧客一覧");
		Page<Customer> list = customerService.getAll(pageable);
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 顧客新規作成画面
	 */
	@GetMapping("/customers/new")
	public ModelAndView customernew(
			@ModelAttribute Customer customer,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "customer/new::customer_contents");
		mav.addObject("title", "顧客新規作成");
		return mav;
	}

//	/**
//	 * 顧客新規作成
//	 * @throws IOException
//	 */
//	@PostMapping("/customers/new")
//	public ModelAndView customersave(
//			@RequestParam("file") MultipartFile[] files,
//			@RequestParam("mailKind") String mailKind,
//			@RequestParam("mailAddr") String mailAddr,
//			@RequestParam("phoneKind") String phoneKind,
//			@RequestParam("phoneNumber") String phoneNumber,
//			@ModelAttribute("customer")
//			@Validated Customer customer,
//			BindingResult result,
//			ModelAndView mav) throws IOException {
//
//		ModelAndView res = null;
//
//
//		if(!result.hasErrors()) {
//			List<TelAudit> list = new ArrayList<TelAudit>();
//			TelAudit telephone = new TelAudit();
//			telephone.setPhoneKind(phoneKind);
//			telephone.setPhoneNumber(phoneNumber);
//			list.add(telephone);
//			customer.setTelephoneList(list);
//			customerService.saveCustomer(customer);
//
//			List<CustomerFile> customerFiles = new ArrayList<CustomerFile>();
//			for(MultipartFile file : files) {
//				CustomerFile customerFile = new CustomerFile();
//				customerFile.setFileName(file.getOriginalFilename());
//				customerFile.setFile(file.getBytes());
//				customerFile.setCustomer(customer);
//				fileService.saveCustomerFile(customerFile);
//				customerFiles.add(customerFile);
//			}
//
//			customer.setCustomerFileList(customerFiles);
//
//			List<MailAudit> maillist = new ArrayList<MailAudit>();
//			MailAudit mailAddress = new MailAudit();
//			mailAddress.setMailKind(mailKind);
//			mailAddress.setMailAddr(mailAddr);
//			mailAddress.setCustomer(customer);
//			mailaddressService.saveCustomerMail(mailAddress);
//			maillist.add(mailAddress);
//			customer.setMailList(maillist);
//
//			res = new ModelAndView("redirect:/customers/" + customer.getId());
//		} else {
//
//			mav.setViewName("layout");
//			mav.addObject("msg", "sorry,error is occured...");
//			mav.addObject("contents", "customer/new::customer_contents");
//			mav.addObject("title", "顧客新規作成");
//			res = mav;
//		}
//		return res;
//	}

	/**
	 * 顧客詳細
	 */
	@GetMapping("/customers/{id}")
	public ModelAndView customershow(
			@PathVariable Integer id,
			ModelAndView mav) {
		Customer customer = customerService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "customer/show::customer_contents");
		mav.addObject("customer", customer);
		mav.addObject("title", "顧客詳細");
		return mav;
	}

	/**
	 * 顧客ファイルダウンロード
	 */
	@GetMapping("customers/{uid}/download/{fid}")
	public void fileDownload(
			@PathVariable Integer uid,
			@PathVariable Integer fid,
			HttpServletResponse response,
			ModelAndView mav
			) {
		try (OutputStream os = response.getOutputStream();) {
			CustomerFile file = fileService.find(fid);
            byte[] bytefile = file.getFile();
            String filename = file.getFileName();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentLength(bytefile.length);
            os.write(bytefile);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

//	/**
//	 * 顧客編集画面
//	 */
//	@GetMapping("customers/{id}/edit")
//	public ModelAndView customeredit(
//			@PathVariable Integer id,
//			ModelAndView mav) {
//		mav.setViewName("layout");
//		mav.addObject("contents", "customer/edit::customer_contents");
//		mav.addObject("title", "顧客編集");
//		mav.addObject("customer", customerService.find(id));
//		return mav;
//	}
//
//	/**
//	 * 顧客編集
//	 */
//	@PostMapping("/customers/{id}/edit")
//	public ModelAndView customeredited(@PathVariable Integer id,
//			@RequestParam(name = "mailKind", required = false) String[] mailKinds,
//			@RequestParam(name = "mailAddr", required = false) String[] mailAddrs,
//			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
//			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
//			@ModelAttribute("customer")
//			@Validated Customer customer,
//			BindingResult result,
//			ModelAndView mav) {
//		Customer editCustomer = customerService.find(id);
//		if(!result.hasErrors()) {
//			editCustomer.setId(id);
//			editCustomer.setName(customer.getName());
//			editCustomer.setKana(customer.getKana());
//			editCustomer.setBirthday(customer.getBirthday());
//			editCustomer.setPerson(customer.getPerson());
//			editCustomer.setMemo(customer.getMemo());
//			if(!editCustomer.getMailList().isEmpty()) {
//				List<CustomerMail> mails = editCustomer.getMailList();
//				for(int i = 0; i < mails.size(); i++) {
//					mails.get(i).setMailKind(mailKinds[i]);
//					mails.get(i).setMailAddr(mailAddrs[i]);
//				}
//			}
//			if(!editCustomer.getTelephoneList().isEmpty()) {
//				List<CustomerTel> tels = editCustomer.getTelephoneList();
//				for(int i = 0; i < tels.size(); i++) {
//					tels.get(i).setPhoneKind(phoneKinds[i]);
//					tels.get(i).setPhoneNumber(phoneNumbers[i]);
//				}
//			}
//
//		} else {
//			mav.setViewName("layout");
//			mav.addObject("contents", "customer/edit::customer_contents");
//			mav.addObject("title", "顧客編集");
//			return mav;
//		}
//		customerService.saveCustomer(editCustomer);
//		return new ModelAndView("redirect:/customers/" + customer.getId());
//	}
	/**
	 * 顧客ファイル追加画面
	 */
	@GetMapping("/customers/{id}/fileedit")
	public ModelAndView fileedit(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "customer/fileedit::customer_contents");
		mav.addObject("title", "顧客ファイル追加");
		mav.addObject("customer", customerService.find(id));
		return mav;
	}

	/**
	 * 顧客ファイル追加
	 */
	@PostMapping("/customers/{id}/fileedit")
	public ModelAndView imgedited(@PathVariable Integer id,
			@RequestParam("file") MultipartFile[] files,
			ModelAndView mav) throws IOException {
		Customer customer = customerService.find(id);
		customer.setId(id);
		List<CustomerFile> customerFiles = new ArrayList<CustomerFile>();
		for(MultipartFile file : files) {
			CustomerFile customerFile = new CustomerFile();
			customerFile.setFileName(file.getOriginalFilename());
			customerFile.setFile(file.getBytes());
			customerFile.setCustomer(customer);
			fileService.saveCustomerFile(customerFile);
			customerFiles.add(customerFile);
		}
		customer.setCustomerFileList(customerFiles);
		customerService.saveCustomer(customer);

		return new ModelAndView("redirect:./");
	}

	/**
	 * 顧客ファイル削除
	 */
	@PostMapping("/customers/{cid}/filedelete/{fid}")
	public ModelAndView customerFiledeleted(
			@PathVariable Integer cid,
			@PathVariable Integer fid,
			ModelAndView mav) {
		Customer customer = customerService.find(cid);
		List<CustomerFile> files = customer.getCustomerFileList();
		CustomerFile customerFile = fileService.find(fid);
		files.remove(customerFile);
		customer.setCustomerFileList(files);
		customerService.saveCustomer(customer);
		fileService.delete(fid);
		return new ModelAndView("redirect:/customers/" + customer.getId());
	}

	/**
	 * 顧客削除
	 */
	@PostMapping("/customers/{id}/delete")
	public ModelAndView customerdelete(@PathVariable Integer id,
			ModelAndView mav) {
		customerService.delete(id);
		return new ModelAndView("redirect:/customers");
	}

	/**
	 * 顧客メールアドレス新規画面
	 */
	@GetMapping("customers/{id}/mailnew")
	public ModelAndView customermailaddressnew(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "mailaddress/customermailaddressnew::customermailaddress_contents");
		mav.addObject("title", "顧客メールアドレス新規登録");
		mav.addObject("customer", customerService.find(id));
		return mav;
	}
//
//	/**
//	 * 顧客メールアドレス新規作成
//	 */
//	@PostMapping("/customers/{id}/mailnew")
//	public ModelAndView usermailaddresssave(@PathVariable Integer id,
//		@RequestParam(name = "mailKind", required = false) String mailKind,
//		@RequestParam(name = "mailAddr", required = false) String mailAddr,
//		ModelAndView mav) {
//			Customer customer = customerService.find(id);
//			CustomerMail mailAddress = new CustomerMail();
//			List<CustomerMail> mails = customer.getMailList();
//			mailAddress.setMailKind(mailKind);
//			mailAddress.setMailAddr(mailAddr);
//			mailAddress.setCustomer(customer);
//			mails.add(mailAddress);
//			mailaddressService.saveCustomerMail(mailAddress);
//			customer.setMailList(mails);
//			customerService.saveCustomer(customer);
//			mav.setViewName("layout");
//			mav.addObject("contents", "mailaddress/customermailaddressnew::customermailaddress_contents");
//			mav.addObject("title", "顧客メールアドレス新規登録");
//			mav.addObject("customer", customer);
//			return new ModelAndView("redirect:/customers/{id}");
//	}
//
//	/**
//	 * 顧客電話新規画面
//	 */
//	@GetMapping("customers/{id}/telnew")
//	public ModelAndView customertelephonenew(@PathVariable Integer id,
//			ModelAndView mav) {
//		mav.setViewName("layout");
//		mav.addObject("contents", "telephone/customertelephonenew::customertelephone_contents");
//		mav.addObject("title", "顧客連絡先新規登録");
//		mav.addObject("customer", customerService.find(id));
//		return mav;
//	}
//
//	/**
//	 * 顧客電話新規作成
//	 */
//	@PostMapping("/customers/{id}/telnew")
//	public ModelAndView usertelephonesave(@PathVariable Integer id,
//		@RequestParam(name = "phoneKind", required = false) String phoneKind,
//		@RequestParam(name = "phoneNumber", required = false) String phoneNumber,
//		ModelAndView mav) {
//			Customer customer = customerService.find(id);
//			CustomerTel tel = new CustomerTel();
//			List<CustomerTel> tels = customer.getTelephoneList();
//			tel.setPhoneKind(phoneKind);
//			tel.setPhoneNumber(phoneNumber);
//			tels.add(tel);
//			customer.setTelephoneList(tels);
//			customerService.saveCustomer(customer);
//			mav.setViewName("layout");
//			mav.addObject("contents", "telephone/customertelephonenew::customertelephone_contents");
//			mav.addObject("title", "顧客連絡先新規登録");
//			mav.addObject("customer", customer);
//			return new ModelAndView("redirect:/customers/{id}");
//	}
//
//	/**
//	 * 顧客メール削除
//	 */
//	@PostMapping("/customers/{uid}/maildelete/{mid}")
//	public ModelAndView customerMaildeleted(
//			@PathVariable Integer uid,
//			@PathVariable int mid,
//			ModelAndView mav) {
//		Customer customer = customerService.find(uid);
//		List<CustomerMail> mails = customer.getMailList();
//		CustomerMail mailAddress = mailaddressService.find(mid);
//		mails.remove(mailAddress);
//		customer.setMailList(mails);
//		customerService.saveCustomer(customer);
//		mailaddressService.delete(mid);
//		return new ModelAndView("redirect:/customers/{uid}");
//	}
//
//	/**
//	 * 顧客電話削除
//	 */
//	@PostMapping("/customers/{uid}/teldelete/{tid}")
//	public ModelAndView customertelephonedeleted(
//			@PathVariable Integer uid,
//			@PathVariable int tid,
//			ModelAndView mav) {
//		Customer customer = customerService.find(uid);
//		List<CustomerTel> tels = customer.getTelephoneList();
//		CustomerTel tel = telephoneService.find(tid);
//		tels.remove(tel);
//		customer.setTelephoneList(tels);
//		customerService.saveCustomer(customer);
//		telephoneService.delete(tid);
//		return new ModelAndView("redirect:/customers/{uid}");
//	}

	/**
	 * 顧客検索
	 */
	@GetMapping("/customers/search")
	public ModelAndView fsearch(
			@RequestParam("name") String search,
			@PageableDefault(page=0, size=5) Pageable pageable) {
		Page<Customer> list = customerService.search(search, search, search, search, pageable);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("layout");
		mav.addObject("contents", "customer/index::customer_contents");
		mav.addObject("title", "顧客検索");
		mav.addObject("list", list);
		return mav;
	}
}
