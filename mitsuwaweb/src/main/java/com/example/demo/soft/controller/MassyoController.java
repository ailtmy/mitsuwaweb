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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.example.demo.service.customer.CompanyService;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.Massyo;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.MassyoService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class MassyoController {

	@Autowired
	MassyoService massyoService;

	@Autowired
	TokisyoService tokisyoService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ShinseiBukkenService bukkenService;

	@Autowired
	TempsyoruiService tempService;

	@Autowired
	KenrisyaService kenrisyaService;

	@Autowired
	GimusyaService gimusyaService;

	/**
	 * 抹消一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Massyo> list = massyoService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/index::massyo_contents");
		mav.addObject("title", "抹消一覧");
		mav.addObject("list", list);
		return mav;

	}

	/**
	 * 抹消新規作成画面
	 * @param massyo
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/new")
	public ModelAndView massyoNew(
			@ModelAttribute Massyo massyo,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/new::massyo_contents");
		mav.addObject("title", "抹消新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/massyo/new")
	public ModelAndView create(
			@ModelAttribute Massyo massyo,
			@RequestParam(name = "tokisyo") Integer tokisyo,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos,
			@RequestParam("gimusya") Integer[] gimusyas,
			@RequestParam(name = "gimusyaaddr", defaultValue=" ") String[] gimusyaaddrs,
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
			@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus
			) {

		List<Kenrisya> kenrisyaList = kenrisyaService.newMassyoKenrisya(new ArrayList<Kenrisya>(), customers,
				addrs, daihyos);
		massyo.setKenrisyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.newMassyoGimusyaList(new ArrayList<Gimusya>(),
				gimusyas, gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		massyo.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		massyo.setShinseiBukkenList(bukkenList);

		massyoService.saveMassyo(massyo);

		return new ModelAndView("redirect:/soft/massyo/" + massyo.getId());
	}

	@GetMapping("/soft/massyo/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/show::massyo_contents");
		mav.addObject("title", "抹消詳細");
		mav.addObject("massyo", massyoService.find(id));
		return mav;
	}






	@GetMapping("/soft/massyo/{id}/create")
	public ModelAndView massyo(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Massyo massyo = massyoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/show::massyo_contents");
		String message = massyoService.xmlFileGet("HM0501201220001",massyo);
		mav.addObject("createMessage", message);
		mav.addObject("title", "抹消作成");
		mav.addObject("massyo", massyo);
		return mav;
	}
}
