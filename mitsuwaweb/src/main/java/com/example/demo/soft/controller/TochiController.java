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

import com.example.demo.soft.entity.Tochi;
import com.example.demo.soft.service.TochiService;

@Controller
public class TochiController {

	@Autowired
	TochiService tochiService;

	/**
	 * 土地一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
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

	/**
	 * 土地新規画面
	 * @param tochi
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tochi/new")
	public ModelAndView tochiNew(
			@ModelAttribute Tochi tochi,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tochi/new::tochi_contents");
		mav.addObject("title", "土地データ新規作成");
		return mav;
	}

	/**
	 * 土地新規作成
	 * @param tochi
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/tochi/new")
	public ModelAndView tochiCreate(
			@ModelAttribute("Tochi") Tochi tochi,
			ModelAndView mav
			) {

		tochiService.saveTochi(tochi);

		return new ModelAndView("redirect:/soft/tochi/" + tochi.getId());
	}

	/**
	 * 土地詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tochi/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		Tochi tochi = tochiService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "tochi/show::tochi_contents");
		mav.addObject("title", "土地詳細");
		mav.addObject("tochi", tochi);
		return mav;
	}

	/**
	 * 土地編集画面
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/tochi/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "tochi/edit::tochi_contents");
		mav.addObject("title", "土地編集");
		mav.addObject("tochi", tochiService.find(id));
		return mav;
	}

	/**
	 * 土地編集
	 * @param id
	 * @param tochi
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/tochi/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Tochi") Tochi tochi,
			ModelAndView mav
			) {
		tochiService.saveTochi(tochi);
		return new ModelAndView("redirect:/soft/tochi/" + tochi.getId());
	}

	@PostMapping("/soft/tochi/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		tochiService.delete(id);
		return new ModelAndView("redirect:/soft/tochi");
	}


}
