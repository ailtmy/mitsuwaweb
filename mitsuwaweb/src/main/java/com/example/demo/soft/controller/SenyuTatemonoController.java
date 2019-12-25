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

import com.example.demo.soft.entity.SenyuTatemono;
import com.example.demo.soft.service.SenyuTatemonoService;

@Controller
public class SenyuTatemonoController {

	@Autowired
	SenyuTatemonoService senyuService;

	/**
	 * 専有部分建物一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<SenyuTatemono> list = senyuService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "senyu/index::senyu_contents");
		mav.addObject("title", "専有部分の建物一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 専有部分建物新規作成画面
	 * @param senyu
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu/new")
	public ModelAndView senyuTatemonoNew(
			@ModelAttribute SenyuTatemono senyu,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "senyu/new::senyu_contents");
		mav.addObject("title", "専有部分建物新規作成");
		return mav;
	}

	/**
	 * 専有部分新規作成
	 * @param senyu
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/senyu/new")
	public ModelAndView senyuCreate(
			@ModelAttribute("SenyuTatemono") SenyuTatemono senyu,
			ModelAndView mav
			) {
		senyuService.saveSenyu(senyu);
		return new ModelAndView("redirect:/soft/senyu/" + senyu.getId());
	}

	/**
	 * 専有部分詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		SenyuTatemono senyu = senyuService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "senyu/show::senyu_contents");
		mav.addObject("title", "専有部分詳細");
		mav.addObject("senyu", senyu);
		return mav;
	}

	/**
	 * 専有部分建物編集画面
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {

		mav.setViewName("layout");
		mav.addObject("contents", "senyu/edit::senyu_contents");
		mav.addObject("title", "専有部分編集");
		mav.addObject("senyu", senyuService.find(id));
		return mav;
	}

	/**
	 * 専有部分編集
	 * @param id
	 * @param senyu
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/senyu/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("SenyuTatemono") SenyuTatemono senyu,
			ModelAndView mav
			) {
		senyuService.saveSenyu(senyu);
		return new ModelAndView("redirect:/soft/senyu/" + senyu.getId());
	}

	@PostMapping("/soft/senyu/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		senyuService.delete(id);
		return new ModelAndView("redirect:/soft/senyu");
	}


}
