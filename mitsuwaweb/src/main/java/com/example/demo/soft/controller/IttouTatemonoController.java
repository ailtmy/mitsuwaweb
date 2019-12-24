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

import com.example.demo.soft.entity.IttouTatemono;
import com.example.demo.soft.service.IttouTatemonoService;

@Controller
public class IttouTatemonoController {

	@Autowired
	IttouTatemonoService ittouService;

	/**
	 * 一棟の建物一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/ittou")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {

		Page<IttouTatemono> list = ittouService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "ittou/index::ittou_contents");
		mav.addObject("title", "一棟建物一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 一棟建物新規画面
	 * @param ittouTatemono
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/ittou/new")
	public ModelAndView ittouNew(
			@ModelAttribute IttouTatemono ittouTatemono,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "ittou/new::ittou_contents");
		mav.addObject("title", "建物データ新規作成");
		return mav;
	}

	/**
	 * 一覧建物新規作成
	 * @param ittouTatemono
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/ittou/new")
	public ModelAndView ittouCreate(
			@ModelAttribute("IttouTatemono") IttouTatemono ittouTatemono,
			ModelAndView mav
			) {

		ittouService.saveIttou(ittouTatemono);

		return new ModelAndView("redirect:/soft/ittou/" + ittouTatemono.getId());
	}

	/**
	 * 一棟建物詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/ittou/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		IttouTatemono ittou = ittouService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "ittou/show::ittou_contents");
		mav.addObject("title", "一棟建物詳細");
		mav.addObject("ittou", ittou);
		return mav;
	}

	/**
	 * 一棟編集画面
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/ittou/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {

		mav.setViewName("layout");
		mav.addObject("contents", "ittou/edit::ittou_contents");
		mav.addObject("title", "一棟の建物編集");
		mav.addObject("ittou", ittouService.find(id));
		return mav;
	}

	@PostMapping("/soft/ittou/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("IttouTatemono") IttouTatemono ittou,
			ModelAndView mav
			) {

		ittouService.saveIttou(ittou);
		return new ModelAndView("redirect:/soft/ittou/" + ittou.getId());
	}

	@PostMapping("/soft/ittou/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		ittouService.delete(id);
		return new ModelAndView("redirect:/soft/ittou");
	}


}
