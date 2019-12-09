package com.example.demo.soft.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.soft.entity.Mishikko;
import com.example.demo.soft.service.MishikkoService;

@Controller
public class MishikkoController {

	@Autowired
	MishikkoService mishikkoService;

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

	@GetMapping("/soft")
	public ModelAndView mishikko(
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {


		mav.setViewName("layout");
		mav.addObject("contents", "soft/mishikkonew::soft_contents");
		Element element = mishikkoService.xmlFileGet();
		mav.addObject("element", element);
		mav.addObject("title", "未失効照会作成");
		return mav;
	}


}
