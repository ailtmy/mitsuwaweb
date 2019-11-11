package com.example.demo.controler;

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

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerMail;
import com.example.demo.entity.CustomerTel;
import com.example.demo.service.CustomerMailService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.CustomerTelService;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerTelService telephoneService;

	@Autowired
	CustomerMailService mailaddressService;

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

	/**
	 * 顧客新規作成
	 */
	@PostMapping("/customers/new")
	public ModelAndView customersave(
			MultipartFile file,
			@RequestParam("mailKind") String mailKind,
			@RequestParam("mailAddr") String mailAddr,
			@RequestParam("phoneKind") String phoneKind,
			@RequestParam("phoneNumber") String phoneNumber,
			@ModelAttribute("customer")
			@Validated Customer customer,
			BindingResult result,
			ModelAndView mav) {

		ModelAndView res = null;


		if(!result.hasErrors()) {
			List<CustomerTel> list = new ArrayList<CustomerTel>();
			CustomerTel telephone = new CustomerTel();
			telephone.setPhoneKind(phoneKind);
			telephone.setPhoneNumber(phoneNumber);
			list.add(telephone);
			customer.setTelephoneList(list);
			customerService.saveCustomer(customer);

			List<CustomerMail> maillist = new ArrayList<CustomerMail>();
			CustomerMail mailAddress = new CustomerMail();
			mailAddress.setMailKind(mailKind);
			mailAddress.setMailAddr(mailAddr);
			mailAddress.setCustomer(customer);
			mailaddressService.saveCustomerMail(mailAddress);
			maillist.add(mailAddress);
			customer.setMailList(maillist);

			res = new ModelAndView("redirect:/customers/" + customer.getId());
		} else {

			mav.setViewName("layout");
			mav.addObject("msg", "sorry,error is occured...");
			mav.addObject("contents", "customer/new::customer_contents");
			mav.addObject("title", "顧客新規作成");
			res = mav;
		}
		return res;
	}

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
	 * 顧客編集画面
	 */
	@GetMapping("customers/{id}/edit")
	public ModelAndView customeredit(
			@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "customer/edit::customer_contents");
		mav.addObject("title", "顧客編集");
		mav.addObject("customer", customerService.find(id));
		return mav;
	}

	/**
	 * 顧客編集
	 */
	@PostMapping("/customers/{id}/edit")
	public ModelAndView customeredited(@PathVariable Integer id,
			@ModelAttribute("customer")
			@Validated Customer customer,
			BindingResult result,
			ModelAndView mav) {
		Customer editCustomer = customerService.find(id);
		if(!result.hasErrors()) {
			editCustomer.setId(id);
			editCustomer.setName(customer.getName());
			editCustomer.setKana(customer.getKana());
			editCustomer.setBirthday(customer.getBirthday());
			editCustomer.setPerson(customer.getPerson());

		} else {
			mav.setViewName("layout");
			mav.addObject("contents", "customer/edit::customer_contents");
			mav.addObject("title", "顧客編集");
			return mav;
		}
		customerService.saveCustomer(editCustomer);
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


















}
