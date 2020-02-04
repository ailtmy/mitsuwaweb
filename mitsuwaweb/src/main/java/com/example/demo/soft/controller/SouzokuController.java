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

import com.example.demo.entity.customer.Customer;
import com.example.demo.service.customer.CompanyService;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Souzoku;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.SouzokuService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class SouzokuController {

	@Autowired
	SouzokuService souzokuService;

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

	/**
	 * 相続一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Souzoku> list = souzokuService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/index::souzoku_contents");
		mav.addObject("title", "相続一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 相続新規作成
	 * @param souzoku
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku/new")
	public ModelAndView souzokuNew(
			@ModelAttribute Souzoku souzoku,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/new::souzoku_contents");
		mav.addObject("title", "相続データ新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/souzoku/new")
	public ModelAndView create(
			@ModelAttribute Souzoku souzoku,
			@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
			@RequestParam("customer") Integer[] customers,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("tokisyo") Integer tokisyo,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos
			) {

		List<Kenrisya> souzokuninList = kenrisyaService.newSyoyuKenrisya(new ArrayList<Kenrisya>(), customers,
				mochibuns, addrs, daihyos);
		souzoku.setSouzokuninList(souzokuninList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		souzoku.setShinseiBukkenList(bukkenList);

		souzokuService.saveSouzoku(souzoku);

		return new ModelAndView("redirect:/soft/souzoku/" + souzoku.getId());
	}

	/**
	 * 相続詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/show::souzoku_contents");
		mav.addObject("title", "相続詳細");
		mav.addObject("souzoku", souzokuService.find(id));
		return mav;
	}

	/**
	 * 相続編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/edit::souzoku_contents");
		mav.addObject("title", "相続編集");
		mav.addObject("souzoku", souzokuService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/souzoku/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Souzoku") Souzoku souzoku,
			@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "shinseiBukken", required = false) Integer[] bukkens,
			@RequestParam("tokisyo") Integer tokisyo,
			@RequestParam("addr") String[] addrs,
			@RequestParam("daihyo") String[] daihyos
			) {

		Souzoku esouzoku = souzokuService.find(id);
		esouzoku.setMokuteki(souzoku.getMokuteki());
		esouzoku.setGenin(souzoku.getGenin());
		esouzoku.setHisouzokunin(souzoku.getHisouzokunin());
		esouzoku.setKenmei(souzoku.getKenmei());
		esouzoku.setTempsyorui(souzoku.getTempsyorui());
		esouzoku.setTokisyo(souzoku.getTokisyo());
		esouzoku.setDate(souzoku.getDate());
		esouzoku.setKazeiGoukei(souzoku.getKazeiGoukei());
		esouzoku.setKazeiUchiwake(souzoku.getKazeiUchiwake());
		esouzoku.setToumenGoukei(souzoku.getToumenGoukei());
		esouzoku.setToumenUchiwake(souzoku.getToumenUchiwake());
		esouzoku.setJyobun(souzoku.getJyobun());

		List<Kenrisya> souzokuninList = kenrisyaService.editSyoyuKenrisya(esouzoku.getSouzokuninList(),
				customers, mochibuns, addrs, daihyos);
		esouzoku.setSouzokuninList(souzokuninList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		esouzoku.setShinseiBukkenList(bukkenList);

		souzokuService.saveSouzoku(esouzoku);

		return new ModelAndView("redirect:/soft/souzoku/{id}");
	}

	/**
	 * 相続削除
	 * @param id
	 * @return
	 */
	@PostMapping("/soft/souzoku/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id
			) {
		souzokuService.delete(id);
		return new ModelAndView("redirect:/soft/souzoku");
	}

	/**
	 * 相続人追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku/{id}/kenrisyanew")
	public ModelAndView souzokuninnew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "相続人追加");
		mav.addObject("souzoku", souzokuService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/souzoku/{id}/kenrisyanew")
	public ModelAndView souzokunincreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "mochibun", required=false, defaultValue=" ") String mochibun,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Souzoku souzoku = souzokuService.find(id);
		List<Kenrisya> souzokuninList = souzoku.getSouzokuninList();
		Kenrisya kenrisya = new Kenrisya();
		kenrisya.setAddr(addr);
		kenrisya.setCustomer(customer);
		kenrisya.setMochibun(mochibun);
		kenrisya.setDaihyo(daihyo);
		kenrisyaService.saveKenrisya(kenrisya);
		souzokuninList.add(kenrisya);
		souzoku.setSouzokuninList(souzokuninList);
		souzokuService.saveSouzoku(souzoku);
		return new ModelAndView("redirect:/soft/souzoku/{id}");
	}

	/**
	 * 相続人削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/souzoku/{id}/souzokunindelete/{sid}")
	public ModelAndView souzokunindelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Souzoku souzoku = souzokuService.find(id);
		List<Kenrisya> souzokuninList = souzoku.getSouzokuninList();
		Kenrisya kenrisya = kenrisyaService.find(sid);
		souzokuninList.remove(kenrisya);
		souzoku.setSouzokuninList(souzokuninList);
		souzokuService.saveSouzoku(souzoku);
		kenrisyaService.delete(sid);

		return new ModelAndView("redirect:/soft/souzoku/{id}");
	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/souzoku/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("souzoku", souzokuService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/souzoku/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid,
			ModelAndView mav
			) {
		Souzoku souzoku = souzokuService.find(id);
		List<ShinseiBukken> bukkens = souzoku.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		souzoku.setShinseiBukkenList(bukkens);
		souzokuService.saveSouzoku(souzoku);

		return new ModelAndView("redirect:/soft/souzoku/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/souzoku/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid
			) {
		Souzoku souzoku = souzokuService.find(sid);
		List<ShinseiBukken> bukkens = souzoku.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		souzoku.setShinseiBukkenList(bukkens);
		souzokuService.saveSouzoku(souzoku);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/souzoku/{sid}");
	}

	/**
	 * 特例オンラインファイル作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/souzoku/{id}/create")
	public ModelAndView souzoku(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Souzoku souzoku = souzokuService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/show::souzoku_contents");
		String message = souzokuService.xmlFileGet("HM0501200520001", souzoku);
		mav.addObject("createMessage", message);
		mav.addObject("title", "相続作成");
		mav.addObject("souzoku", souzoku);
		return mav;
	}

	/**
	 * QR書面申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/souzoku/{id}/qrcreate")
	public ModelAndView qrsouzoku(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Souzoku souzoku = souzokuService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "souzoku/show::souzoku_contents");
		String message = souzokuService.xmlFileGet("HM0508200520001", souzoku);
		mav.addObject("createMessage", message);
		mav.addObject("title", "相続QR作成");
		mav.addObject("souzoku", souzoku);
		return mav;
	}

}
