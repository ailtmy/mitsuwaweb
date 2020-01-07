package com.example.demo.soft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.service.HozonService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class HozonController {

	@Autowired
	HozonService hozonService;

	@Autowired
	TokisyoService tokisyoService;

	@Autowired
	CustomerService customerService;

	@Autowired
	ShinseiBukkenService bukkenService;

	@Autowired
	TempsyoruiService tempService;

	/**
	 * ２項保存一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Hozon> list = hozonService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/index::hozon_contents");
		mav.addObject("title", "２項保存一覧");
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("/soft/hozon/new")
	public ModelAndView hozonNew(
			@ModelAttribute Hozon hozon,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/new::hozon_contents");
		mav.addObject("title", "２項保存データ新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/hozon/new")
	public ModelAndView hozonCreate(
			ModelAndView mav,
			@ModelAttribute Hozon hozon,
			@RequestParam("customer") Integer[] customers,
			@RequestParam("shinseiBukken") Integer[] bukkens
			) {
		System.out.println(customers.getClass());
		System.out.println(customers);
		System.out.println(bukkens.getClass());
		System.out.println(bukkens);
		hozonService.saveHozon(hozon);
		return new ModelAndView("redirect:/soft/hozon");
	}
}