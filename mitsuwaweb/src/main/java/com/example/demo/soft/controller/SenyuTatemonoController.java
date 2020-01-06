package com.example.demo.soft.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.soft.entity.FuzokuTatemono;
import com.example.demo.soft.entity.SenyuTatemono;
import com.example.demo.soft.entity.Shikichiken;
import com.example.demo.soft.service.FuzokuTatemonoService;
import com.example.demo.soft.service.SenyuTatemonoService;
import com.example.demo.soft.service.ShikichikenService;

@Controller
public class SenyuTatemonoController {

	@Autowired
	SenyuTatemonoService senyuService;

	@Autowired
	FuzokuTatemonoService fuzokuService;

	@Autowired
	ShikichikenService shikichiService;

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
			ModelAndView mav,
			@ModelAttribute("SenyuTatemono") SenyuTatemono senyu,
			@ModelAttribute("FuzokuTatemono") FuzokuTatemono fuzokus,
			@ModelAttribute("Shikichiken") Shikichiken shikichis
			) {

		if(fuzokus.getFuzokufugo() != null) {
			List<FuzokuTatemono> fuzokuTatemono = new ArrayList<FuzokuTatemono>();
			if(fuzokus.getFuzokufugo().isEmpty()){
			} else {
				for(int i = 0; i < fuzokus.getFuzokufugo().split(",").length; i++) {
					FuzokuTatemono fuzoku = new FuzokuTatemono();
					fuzoku.setFuzokufugo(fuzokus.getFuzokufugo().split(",")[i]);
					fuzoku.setFuzokusyurui(fuzokus.getFuzokusyurui().split(",")[i]);
					fuzoku.setFuzokukozo(fuzokus.getFuzokukozo().split(",")[i]);
					fuzoku.setFuzokuyukamenseki(fuzokus.getFuzokuyukamenseki().split(",")[i]);
					fuzoku.setFuzokubiko(fuzokus.getFuzokubiko().split(",")[i]);
					fuzokuService.saveFuzoku(fuzoku);
					fuzokuTatemono.add(fuzoku);
				}
				senyu.setFuzokuTatemono(fuzokuTatemono);
			}
		}

		if(shikichis.getShikichifugo() != null) {
			List<Shikichiken> shikichiList = new ArrayList<Shikichiken>();
			if(shikichis.getShikichifugo().isEmpty()) {
			} else {
				for(int i = 0; i < shikichis.getShikichifugo().split(",").length; i++) {
					Shikichiken shikichi = new Shikichiken();
					shikichi.setShikichifugo(shikichis.getShikichifugo().split(",")[i]);
					shikichi.setShikichisyozaichiban(shikichis.getShikichisyozaichiban().split(",")[i]);
					shikichi.setShikichichimoku(shikichis.getShikichichimoku().split(",")[i]);
					shikichi.setShikichichiseki(shikichis.getShikichichiseki().split(",")[i]);
					shikichi.setShikichisyurui(shikichis.getShikichisyurui().split(",")[i]);
					shikichi.setShikichiwariai(shikichis.getShikichiwariai().split(",")[i]);
					shikichi.setShikichibiko(shikichis.getShikichibiko().split(",")[i]);
					shikichiService.saveShikichiken(shikichi);
					shikichiList.add(shikichi);
				}
				senyu.setShikichiken(shikichiList);
			}
		}

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
			@ModelAttribute("FuzokuTatemono") FuzokuTatemono fuzokus,
			@ModelAttribute("Shikichiken") Shikichiken shikichis,
			ModelAndView mav
			) {
		if(fuzokus.getFuzokufugo() != null) {
			List<FuzokuTatemono> fuzokuTatemono = senyuService.find(id).getFuzokuTatemono();
			for(int i = 0; i < fuzokus.getFuzokufugo().split(",").length; i++) {
				FuzokuTatemono fuzoku;
				if(fuzokuTatemono.isEmpty() || fuzokuTatemono == null) {
					fuzoku = new FuzokuTatemono();
					fuzokuTatemono.add(fuzoku);
				} else {
					fuzoku = fuzokuService.find(fuzokuTatemono.get(i));
				}
				fuzoku.setFuzokufugo(fuzokus.getFuzokufugo().split(",")[i]);
				fuzoku.setFuzokusyurui(fuzokus.getFuzokusyurui().split(",")[i]);
				fuzoku.setFuzokukozo(fuzokus.getFuzokukozo().split(",")[i]);
				fuzoku.setFuzokuyukamenseki(fuzokus.getFuzokuyukamenseki().split(",")[i]);
				fuzoku.setFuzokubiko(fuzokus.getFuzokubiko().split(",")[i]);
				fuzokuService.saveFuzoku(fuzoku);
			}
			senyu.setFuzokuTatemono(fuzokuTatemono);
		}

		if(shikichis.getShikichifugo() != null) {
			List<Shikichiken> shikichiList = senyuService.find(id).getShikichiken();
			for(int i = 0; i < shikichis.getShikichifugo().split(",").length; i++) {
				Shikichiken shikichi;
				if(shikichiList.isEmpty() || shikichiList == null) {
					shikichi = new Shikichiken();
					shikichiList.add(shikichi);
				} else {
					shikichi = shikichiService.find(shikichiList.get(i));
				}
				shikichi.setShikichifugo(shikichis.getShikichifugo().split(",")[i]);
				shikichi.setShikichisyozaichiban(shikichis.getShikichisyozaichiban().split(",")[i]);
				shikichi.setShikichichimoku(shikichis.getShikichichimoku().split(",")[i]);
				shikichi.setShikichichiseki(shikichis.getShikichichiseki().split(",")[i]);
				shikichi.setShikichisyurui(shikichis.getShikichisyurui().split(",")[i]);
				shikichi.setShikichiwariai(shikichis.getShikichiwariai().split(",")[i]);
				shikichi.setShikichibiko(shikichis.getShikichibiko().split(",")[i]);
			}
			senyu.setShikichiken(shikichiList);
		}
		senyuService.saveSenyu(senyu);
		return new ModelAndView("redirect:/soft/senyu/" + senyu.getId());
	}

	/**
	 * 専有部分削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/senyu/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		senyuService.delete(id);
		return new ModelAndView("redirect:/soft/senyu");
	}

	/**
	 * 専有附属追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu/{id}/fuzokuadd")
	public ModelAndView fuzokuNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "senyu/fuzokuadd::senyu_contents");
		mav.addObject("title", "附属建物追加");
		mav.addObject("senyu", senyuService.find(id));
		return mav;
	}

	@PostMapping("/soft/senyu/{id}/fuzokuadd")
	public ModelAndView fuzokuCreate(
			ModelAndView mav,
			@PathVariable Integer id,
 			@ModelAttribute("FuzokuTatemono") FuzokuTatemono fuzoku
			) {
		SenyuTatemono senyu = senyuService.find(id);
		List<FuzokuTatemono> fuzokus = senyu.getFuzokuTatemono();
		FuzokuTatemono fuzokuTatemono = new FuzokuTatemono(fuzoku);
		fuzokuService.saveFuzoku(fuzokuTatemono);
		fuzokus.add(fuzokuTatemono);
		senyu.setFuzokuTatemono(fuzokus);
		senyuService.saveSenyu(senyu);
		return new ModelAndView("redirect:/soft/senyu/{id}") ;
	}

	/**
	 * 専有附属建物削除
	 * @param tid
	 * @param fid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/senyu/{sid}/fuzokudelete/{fid}")
	public ModelAndView fuzokuDeleted(
			@PathVariable Integer sid,
			@PathVariable Integer fid,
			ModelAndView mav
			) {
		SenyuTatemono senyu = senyuService.find(sid);
		List<FuzokuTatemono> fuzokus = senyu.getFuzokuTatemono();
		FuzokuTatemono fuzoku = fuzokuService.findById(fid);
		fuzokus.remove(fuzoku);
		senyu.setFuzokuTatemono(fuzokus);
		senyuService.saveSenyu(senyu);
		fuzokuService.delete(fuzoku);
		return new ModelAndView("redirect:/soft/senyu/{sid}");
	}

	/**
	 * 敷地権追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/senyu/{id}/shikichiadd")
	public ModelAndView shikichiNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "senyu/shikichiadd::senyu_contents");
		mav.addObject("title", "敷地権追加");
		mav.addObject("senyu", senyuService.find(id));
		return mav;
	}

	@PostMapping("/soft/senyu/{id}/shikichiadd")
	public ModelAndView shikichiCreate(
			@PathVariable Integer id,
			@ModelAttribute("Shikichiken") Shikichiken shikichi,
			ModelAndView mav
			) {
		SenyuTatemono senyu = senyuService.find(id);
		List<Shikichiken> shikichis = senyu.getShikichiken();
		Shikichiken shikichiken = new Shikichiken(shikichi);
		shikichiService.saveShikichiken(shikichiken);
		shikichis.add(shikichiken);
		senyu.setShikichiken(shikichis);
		senyuService.saveSenyu(senyu);
		return new ModelAndView("redirect:/soft/senyu/{id}");
	}

	/**
	 * 敷地権削除
	 * @param id
	 * @param sid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/senyu/{id}/shikichidelete/{sid}")
	public ModelAndView shikichiDeleted(
			@PathVariable Integer id,
			@PathVariable Integer sid,
			ModelAndView mav
			) {
		SenyuTatemono senyu = senyuService.find(id);
		List<Shikichiken> shikichis = senyu.getShikichiken();
		Shikichiken shikichi = shikichiService.findById(sid);
		shikichis.remove(shikichi);
		senyu.setShikichiken(shikichis);
		senyuService.saveSenyu(senyu);
		shikichiService.delete(shikichi);
		return new ModelAndView("redirect:/soft/senyu/{id}");
	}
}
