package com.example.demo.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.honninkakunin.HonninKakunin;
import com.example.demo.entity.user.User;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.service.honninkakunin.HonninKakuninService;
import com.example.demo.service.user.UserService;

@Controller
public class HonninKakuninController {

	@Autowired
	HonninKakuninService honninKakuninService;

	@Autowired
	CustomerService customerService;

	@Autowired
	UserService userService;

	/**
	 * 顧客別本人確認記録
	 */
	@GetMapping("/customers/{id}/idents")
	public ModelAndView identIndex(
			@PathVariable Integer id,
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav) {
		Customer customer = customerService.find(id);
		Page<HonninKakunin> list = honninKakuninService.getByCustomer(customer, pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "honninkakunin/index::honnninkakunin_contents");
		mav.addObject("title", "本人確認記録");
		mav.addObject("list", list);
		mav.addObject("customer",customer);
		return mav;
	}

	/**
	 * 対面取引本人確認記録新規作成画面
	 */
	@GetMapping("/customers/{id}/ident/tnew")
	public ModelAndView tnew(
			@PathVariable Integer id,
			@ModelAttribute HonninKakunin honninkakunin,
			ModelAndView mav) {
		Customer customer = customerService.find(id);
		List<User> users = userService.findAll();
		mav.setViewName("layout");
		mav.addObject("contents", "honninkakunin/taimennew::honninkakunin_contents");
		mav.addObject("title", "対面取引本人確認記録作成");
		mav.addObject("customer", customer);
		mav.addObject("users", users);
		return mav;
	}

	/**
	 * 対面取引本人確認記録作成
	 */
//	@PostMapping("/customers/{id}/idents/tnew")
//	public ModelAndView tcreat(
//			@PathVariable Integer id,
//			@RequestParam("file") MultipartFile[] files,
//			@RequestParam("addrkind") String addrkind,
//			@RequestParam("zipcode") String zipcode,
//			@RequestParam("addr") String addr,

//			@ModelAttribute Customer customer,
//			@ModelAttribute TaimenTorihiki taimentorihiki,
//			@ModelAttribute CustomerAddress customeraddress,
//			@ModelAttribute HonninKakunin honninkakunin,
//			ModelAndView mav) {
//
//		honninKakuninService.saveHonninKakunin(honninkakunin);
//		mav.setViewName("layout");
//		mav.addObject("contents", "honninkakunin/index::honninkakunin_contents");
//		mav.addObject("title", "本人確認記録作成");
//		mav.addObject("honninkakunin", honninkakunin);
//
//		return new ModelAndView("redirect: /");
//	}


}
