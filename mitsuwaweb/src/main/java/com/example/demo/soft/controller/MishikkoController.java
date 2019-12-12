package com.example.demo.soft.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.example.demo.soft.entity.Mishikko;
import com.example.demo.soft.entity.TaisyoBukken;
import com.example.demo.soft.service.MishikkoService;
import com.example.demo.soft.service.TaisyoBukkenService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class MishikkoController {

	@Autowired
	MishikkoService mishikkoService;

	@Autowired
	TokisyoService tokisyoService;

	@Autowired
	TaisyoBukkenService bukkenService;

	/**
	 * 未失効一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/mishikko")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {

		Page<Mishikko> list = mishikkoService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "mishikko/index::mishikko_contents");
		mav.addObject("title", "未失効照会一覧");
		mav.addObject("list", list);
		return mav;

	}

	/**
	 * 未失効照会データ新規画面
	 * @param mishikko
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/mishikko/new")
	public ModelAndView mishikkoNew(
			@ModelAttribute Mishikko mishikko,
			ModelAndView mav
			) {
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.setViewName("layout");
		mav.addObject("contents", "mishikko/new::mishikko_contents");
		mav.addObject("title", "未失効照会データ新規作成");
		return mav;

	}

	@PostMapping("/soft/mishikko/new")
	public ModelAndView mishikkoCreate(
		@ModelAttribute("Mishikko") Mishikko mishikko,
		@ModelAttribute("TaisyoBukken") TaisyoBukken bukken,
		@PageableDefault(page=0, size=5)
		Pageable pageable,
		ModelAndView mav
			) {

		bukkenService.saveBukken(bukken);
		List<TaisyoBukken> bukkenList = new ArrayList<TaisyoBukken>();
		bukkenList.add(bukken);
		mishikko.setTaisyoBukkenList(bukkenList);
		mishikkoService.saveMishikko(mishikko);
		return new ModelAndView("redirect:./" + mishikko.getId());
	}


	@GetMapping("/soft/mishikko/{id}")
	public ModelAndView show(
		@PathVariable Integer id,
		ModelAndView mav
			) {
		Mishikko mishikko = mishikkoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "mishikko/show::mishikko_contents");
		mav.addObject("title", "未失効照会詳細");
		mav.addObject("mishikko", mishikko);
		return mav;
	}


	@GetMapping("/soft/mishikko/{id}/create")
	public ModelAndView mishikko(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		Mishikko mishikko = mishikkoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "mishikko/show::mishikko_contents");
		String message = mishikkoService.xmlFileGet("HM0504000300001", mishikko);
		mav.addObject("createMessage", message);
		mav.addObject("title", "未失効照会作成");
		mav.addObject("mishikko", mishikko);
		return mav;
	}


}
