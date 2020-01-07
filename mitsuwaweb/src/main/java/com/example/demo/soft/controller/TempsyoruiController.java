package com.example.demo.soft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.soft.entity.Tempsyorui;
import com.example.demo.soft.service.TempsyoruiService;

@Controller
public class TempsyoruiController {

	@Autowired
	TempsyoruiService service;

	/**
	 * 添付書類一覧
	 * @param mav
	 * @param pageable
	 * @return
	 */
	@GetMapping("/soft/tempsyorui")
	public ModelAndView index(
			ModelAndView mav,
			@PageableDefault(page=0, size=5)
			Pageable pageable
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tempsyorui/index::tempsyorui_contents");
		mav.addObject("title", "添付書類一覧");
		mav.addObject("list", service.getAll(pageable));
		return mav;
	}

	/**
	 * 添付書類パターン作成
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tempsyorui/new")
	public ModelAndView tempNew(
			@ModelAttribute Tempsyorui tempsyorui,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tempsyorui/new::tempsyorui_contents");
		mav.addObject("title", "添付書類パターン作成");
		return mav;
	}

	@PostMapping("/soft/tempsyorui/new")
	public ModelAndView tempCreate(
			ModelAndView mav,
			@RequestParam("pattern") String pattern,
			@RequestParam("syorui") String[] syoruiList
			) {

		Tempsyorui tempsyorui = new Tempsyorui();
		tempsyorui.setPattern(pattern);
		tempsyorui.setSyoruis(syoruiList);
		service.saveTempsyorui(tempsyorui);
		return new ModelAndView("redirect:/soft/tempsyorui");

	}

	/**
	 * 添付書類詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tempsyorui/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tempsyorui/show::tempsyorui_contents");
		mav.addObject("title", "添付書類詳細");
		mav.addObject("tempsyorui", service.find(id));
		return mav;
	}

	/**
	 * 添付書類編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tempsyorui/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tempsyorui/edit::tempsyorui_contents");
		mav.addObject("title", "添付書類編集");
		mav.addObject("tempsyorui", service.find(id));
		return mav;
	}

	@PostMapping("/soft/tempsyorui/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@RequestParam("pattern") String pattern,
			@RequestParam("syorui") String[] syoruis,
			ModelAndView mav
			) {

		Tempsyorui tempsyorui = service.find(id);
		tempsyorui.setPattern(pattern);
		tempsyorui.setSyoruis(syoruis);
		service.saveTempsyorui(tempsyorui);
		return new ModelAndView("redirect:/soft/tempsyorui/{id}");
	}

	/**
	 * 添付書類削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/tempsyorui/{id}/delete")
	public ModelAndView deleted(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		service.delete(id);
		return new ModelAndView("redirect:/soft/tempsyorui");

	}

}
