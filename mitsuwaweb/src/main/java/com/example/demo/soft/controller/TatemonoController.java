package com.example.demo.soft.controller;

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

import com.example.demo.soft.entity.Tatemono;
import com.example.demo.soft.service.TatemonoService;

@Controller
public class TatemonoController {

	@Autowired
	TatemonoService tatemonoService;

	/**
	 * 建物一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tatemono")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Tatemono> list = tatemonoService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "tatemono/index::tatemono_contents");
		mav.addObject("title", "建物一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 建物新規画面
	 * @param tatemono
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tatemono/new")
	public ModelAndView tatemonoNew(
			@ModelAttribute Tatemono tatemono,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tatemono/new::tatemono_contents");
		mav.addObject("title", "建物データ新規作成");
		return mav;
	}

	/**
	 * 建物新規作成
	 * @param tatemono
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/tatemono/new")
	public ModelAndView tatemonoCreate(
			@ModelAttribute("Tatemono") Tatemono tatemono,
			ModelAndView mav
			) {

		tatemonoService.saveTatemono(tatemono);

		return new ModelAndView("redirect:/soft/tatemono/" + tatemono.getId());
	}

	/**
	 * 建物詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tatemono/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		Tatemono tatemono = tatemonoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "tatemono/show::tatemono_contents");
		mav.addObject("title", "建物詳細");
		mav.addObject("tatemono", tatemono);
		return mav;
	}

	/**
	 * 建物編集画面
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tatemono/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {

		mav.setViewName("layout");
		mav.addObject("contents", "tatemono/edit::tatemono_contents");
		mav.addObject("title", "建物編集");
		mav.addObject("tatemono", tatemonoService.find(id));
		return mav;
	}

	@PostMapping("/soft/tatemono/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Tatemono") Tatemono tatemono,
			ModelAndView mav
			) {

		tatemonoService.saveTatemono(tatemono);
		return new ModelAndView("redirect:/soft/tatemono/" + tatemono.getId());
	}

	@PostMapping("/soft/tatemono/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		tatemonoService.delete(id);
		return new ModelAndView("redirect:/soft/tatemono");
	}




}
