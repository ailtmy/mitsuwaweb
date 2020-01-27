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
import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.Saimusya;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Teitouken;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.SaimusyaService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TeitoukenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class TeitoukenController {

	@Autowired
	TeitoukenService teitouService;

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
	SaimusyaService saimusyaService;

	@Autowired
	GimusyaService gimusyaService;

	/**
	 * 抵当権設定一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Teitouken> list = teitouService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/index::teitou_contents");
		mav.addObject("title", "抵当権設定一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 抵当権設定新規作成画面
	 * @param teitouken
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/new")
	public ModelAndView teitouNew(
		@ModelAttribute Teitouken teitouken,
		ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/new::teitou_contents");
		mav.addObject("title", "抵当権設定新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;

	}

	@PostMapping("/soft/teitou/new")
	public ModelAndView teitouCreate(
			ModelAndView mav,
			@ModelAttribute Teitouken teitouken,
			@RequestParam(name = "tokisyo") Integer tokisyo,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("saimusya") Integer[] saimusyas,
			@RequestParam(name = "saimusyaaddr", defaultValue=" ") String[] saimusyaaddrs,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos,
			@RequestParam(name = "shiten", defaultValue=" ") String[] shitens,
			@RequestParam("gimusya") Integer[] gimusyas,
			@RequestParam(name = "gimusyaaddr", defaultValue=" ") String[] gimusyaaddrs,
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos
			) {

		List<Saimusya> saimusyaList = new ArrayList<Saimusya>();
		for(int i = 0; i < saimusyas.length; i++) {
			Saimusya saimusya = new Saimusya();
			Customer saimusyaCustomer = customerService.find(saimusyas[i]);
			saimusya.setCustomer(saimusyaCustomer);
			if(saimusyaaddrs[i] != null || !(saimusyaaddrs[i].isEmpty())) {
				saimusya.setAddr(saimusyaaddrs[i]);
			}
			saimusyaService.saveSaimusya(saimusya);
			saimusyaList.add(saimusya);
		}
		teitouken.setSaimusyaList(saimusyaList);

		List<Kenrisya> kenrisyaList = new ArrayList<Kenrisya>();
		for(int i = 0; i < customers.length; i++) {
			Kenrisya kenrisya = new Kenrisya();
			Customer kenrisyaCustomer = customerService.find(customers[i]);
			kenrisya.setCustomer(kenrisyaCustomer);
			if(addrs[i] != null || !(addrs[i].isEmpty())) {
				kenrisya.setAddr(addrs[i]);
			}
			if(daihyos[i] != null || !(daihyos[i].isEmpty())) {
				kenrisya.setDaihyo(daihyos[i]);
			}
			if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
				kenrisya.setMochibun(mochibuns[i]);
			}
			if(shitens[i] != null || !(shitens[i].isEmpty())) {
				kenrisya.setShiten(shitens[i]);
			}
			kenrisyaService.saveKenrisya(kenrisya);
			kenrisyaList.add(kenrisya);
		}
		teitouken.setTeitoukensyaList(kenrisyaList);

		List<Gimusya> gimusyaList = new ArrayList<Gimusya>();
		for(int i = 0; i < gimusyas.length; i++) {
			Gimusya gimusya = new Gimusya();
			Customer gimusyaCustomer = customerService.find(gimusyas[i]);
			gimusya.setCustomer(gimusyaCustomer);
			if(gimusyaaddrs[i] != null || !(gimusyaaddrs[i].isEmpty())) {
				gimusya.setAddr(gimusyaaddrs[i]);
			}
			if(gimusyadaihyos[i] != null || !(gimusyadaihyos[i].isEmpty())) {
				gimusya.setDaihyo(gimusyadaihyos[i]);
			}
			gimusyaService.saveGimusya(gimusya);
			gimusyaList.add(gimusya);
		}
		teitouken.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		teitouken.setShinseiBukkenList(bukkenList);

		teitouService.saveTeitouken(teitouken);

		return new ModelAndView("redirect:/soft/teitou/" + teitouken.getId());
	}

	/**
	 * 抵当権設定詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/show::teitou_contents");
		mav.addObject("title", "抵当権設定詳細");
		mav.addObject("teitou", teitouService.find(id));
		return mav;
	}

	/**
	 * 抵当権設定編集
	 * @param id
	 * @param teitouken
	 * @param tokisyo
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			@ModelAttribute Teitouken teitouken,
			@RequestParam("tokisyo") Integer tokisyo,
			ModelAndView mav
			) {

		mav.setViewName("layout");
		mav.addObject("contents", "teitou/edit::teitou_contents");
		mav.addObject("title", "抵当権設定編集");
		mav.addObject("teitou", teitouService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/teitou/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Teitouken") Teitouken teitouken,
			@RequestParam("tokisyo") Integer tokisyo,
			ModelAndView mav
			) {

		return new ModelAndView("redirect:/soft/teitou/{id}");
	}

	/**
	 * 抵当権設定削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/teitou/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		teitouService.delete(id);
		return new ModelAndView("redirect:/soft/teitou");
	}

	/**
	 * 抵当権設定　債務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}/saimusyanew")
	public ModelAndView saimusyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/saimusyanew::saimusya_contents");
		mav.addObject("title", "債務者追加");
		mav.addObject("teitou", teitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/teitou/{id}/saimusyanew")
	public ModelAndView saimusaycreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Saimusya> saimusyas = teitouken.getSaimusyaList();
		Saimusya saimusya = new Saimusya();
		saimusya.setAddr(addr);
		saimusya.setCustomer(customer);
		saimusyaService.saveSaimusya(saimusya);
		saimusyas.add(saimusya);
		teitouken.setSaimusyaList(saimusyas);
		teitouService.saveTeitouken(teitouken);

		return new ModelAndView("redirect:/soft/teitou/{id}");
	}

	/**
	 * 債務者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/teitou/{id}/saimusyadelete/{sid}")
	public ModelAndView saimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Saimusya> saimusyas = teitouken.getSaimusyaList();
		Saimusya saimusya = saimusyaService.find(sid);
		saimusyas.remove(saimusya);
		teitouken.setSaimusyaList(saimusyas);
		teitouService.saveTeitouken(teitouken);
		saimusyaService.delete(sid);

		return new ModelAndView("redirect:/soft/teitou/{id}");

	}

	/**
	 * 抵当権設定　抵当権者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}/kenrisyanew")
	public ModelAndView kenrisyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "抵当権者追加");
		mav.addObject("teitou", teitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/teitou/{id}/kenrisyanew")
	public ModelAndView kenrisyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "mochibun", required=false, defaultValue=" ") String mochibun,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "shiten", required=false) String shiten,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Kenrisya> kenrisyas = teitouken.getTeitoukensyaList();
		Kenrisya kenrisya = new Kenrisya();
		kenrisya.setAddr(addr);
		kenrisya.setMochibun(mochibun);
		kenrisya.setCustomer(customer);
		kenrisya.setShiten(shiten);
		kenrisya.setDaihyo(daihyo);
		kenrisyaService.saveKenrisya(kenrisya);
		kenrisyas.add(kenrisya);
		teitouken.setTeitoukensyaList(kenrisyas);
		teitouService.saveTeitouken(teitouken);

		return new ModelAndView("redirect:/soft/teitou/{id}");
	}

	/**
	 * 抵当権者者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/teitou/{id}/kenrisyadelete/{sid}")
	public ModelAndView kenrisyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Kenrisya> kenrisyas = teitouken.getTeitoukensyaList();
		Kenrisya kenrisya = kenrisyaService.find(sid);
		kenrisyas.remove(kenrisya);
		teitouken.setTeitoukensyaList(kenrisyas);
		teitouService.saveTeitouken(teitouken);
		kenrisyaService.delete(sid);

		return new ModelAndView("redirect:/soft/teitou/{id}");

	}

	/**
	 * 抵当権設定　義務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}/gimusyanew")
	public ModelAndView gimusyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/gimusyanew::gimusya_contents");
		mav.addObject("title", "義務者追加");
		mav.addObject("teitou", teitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/teitou/{id}/gimusyanew")
	public ModelAndView gimusyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "mochibun", required=false, defaultValue=" ") String mochibun,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "shiten", required=false) String shiten,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Gimusya> gimusyas = teitouken.getGimusyaList();
		Gimusya gimusya = new Gimusya();
		gimusya.setAddr(addr);
		gimusya.setCustomer(customer);
		gimusya.setDaihyo(daihyo);
		gimusyaService.saveGimusya(gimusya);
		gimusyas.add(gimusya);
		teitouken.setGimusyaList(gimusyas);
		teitouService.saveTeitouken(teitouken);

		return new ModelAndView("redirect:/soft/teitou/{id}");
	}

	/**
	 * 抵当権者者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/teitou/{id}/gimusyadelete/{sid}")
	public ModelAndView gimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Teitouken teitouken = teitouService.find(id);
		List<Gimusya> gimusyas = teitouken.getGimusyaList();
		Gimusya gimusya = gimusyaService.find(sid);
		gimusyas.remove(gimusya);
		teitouken.setGimusyaList(gimusyas);
		teitouService.saveTeitouken(teitouken);
		gimusyaService.delete(sid);

		return new ModelAndView("redirect:/soft/teitou/{id}");

	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/teitou/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "bukken/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("teitou", teitouService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/teitou/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid,
			ModelAndView mav
			) {
		Teitouken teitou = teitouService.find(id);
		List<ShinseiBukken> bukkens = teitou.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		teitou.setShinseiBukkenList(bukkens);
		teitouService.saveTeitouken(teitou);

		return new ModelAndView("redirect:/soft/teitou/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/teitou/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid,
			ModelAndView mav
			) {
		Teitouken teitou = teitouService.find(sid);
		List<ShinseiBukken> bukkens = teitou.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		teitou.setShinseiBukkenList(bukkens);
		teitouService.saveTeitouken(teitou);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/teitou/{sid}");
	}

	/**
	 * オンライン特例申請ファイル作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/teitou/{id}/create")
	public ModelAndView teitou(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Teitouken teitou = teitouService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/show::teitou_contents");
		String message = teitouService.xmlFileGet("HM0501201020001",teitou);
		mav.addObject("createMessage", message);
		mav.addObject("title", "抵当権設定作成");
		mav.addObject("teitou", teitou);
		return mav;
	}

	/**
	 * QRコード申請書作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/teitou/{id}/qrcreate")
	public ModelAndView qrteitou(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Teitouken teitou = teitouService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "teitou/show::teitou_contents");
		String message = teitouService.xmlFileGet("HM0508201020001", teitou);
		mav.addObject("createMessage", message);
		mav.addObject("title", "抵当権設定QR作成");
		mav.addObject("teitou", teitou);
		return mav;
	}










}
