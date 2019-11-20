package com.example.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.honninkakunin.HonninKakunin;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.service.honninkakunin.HonninKakuninService;

@Controller
public class HonninKakuninController {

	@Autowired
	HonninKakuninService honninKakuninService;

	@Autowired
	CustomerService customerService;

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


}
