package com.example.demo.soft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.soft.entity.Tochi;
import com.example.demo.soft.service.TochiService;

@Controller
public class TochiController {

	@Autowired
	TochiService tochiService;

	@GetMapping("/soft/tochi")
	public ModelAndView tochiIndex(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Tochi> list = tochiService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "tochi/index::tochi_contents");
		mav.addObject("title", "土地一覧");
		mav.addObject("list", list);
		return mav;
	}

}
