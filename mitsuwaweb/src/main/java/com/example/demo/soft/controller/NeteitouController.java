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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.customer.Customer;
import com.example.demo.service.customer.CompanyService;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.Neteitou;
import com.example.demo.soft.entity.Saimusya;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.NeteitouService;
import com.example.demo.soft.service.SaimusyaService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class NeteitouController {

	@Autowired
	NeteitouService neteitouService;

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
	 * 根抵当権設定一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Neteitou> list = neteitouService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/index::neteitou_contents");
		mav.addObject("title", "根抵当権設定一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 根抵当権設定新規作成
	 * @param neteitou
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/new")
	public ModelAndView neteitouNew(
		@ModelAttribute Neteitou neteitou,
		ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/new::neteitou_contents");
		mav.addObject("title", "根抵当権設定新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;

	}

	@PostMapping("/soft/neteitou/new")
	public ModelAndView neteitouCreate(
			ModelAndView mav,
			@ModelAttribute Neteitou neteitou,
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
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
			@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus
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
		neteitou.setSaimusyaList(saimusyaList);


		List<Kenrisya> kenrisyaList = kenrisyaService.newTeitouKenrisya(new ArrayList<Kenrisya>(), customers, mochibuns, addrs,
				daihyos, shitens);
		neteitou.setNeteitoukensyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.newMassyoGimusyaList(new ArrayList<Gimusya>(), gimusyas,
				gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		neteitou.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		neteitou.setShinseiBukkenList(bukkenList);

		neteitouService.saveNeteitou(neteitou);

		return new ModelAndView("redirect:/soft/neteitou/" + neteitou.getId());
	}

	/**
	 * 根抵当権設定　詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/show::neteitou_contents");
		mav.addObject("title", "根抵当権設定詳細");
		mav.addObject("neteitou", neteitouService.find(id));
		return mav;
	}

	/**
	 * 根抵当権設定　編集画面
	 * @param id
	 * @param neteitou
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			@ModelAttribute Neteitou neteitou,
			ModelAndView mav
			) {

		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/edit::neteitou_contents");
		mav.addObject("title", "根抵当権設定編集");
		mav.addObject("neteitou", neteitouService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/neteitou/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Neteitou") Neteitou neteitou,
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
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
			@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus,
			ModelAndView mav
			) {

		Neteitou eneteitou = neteitouService.find(id);
		eneteitou.setKenmei(neteitou.getKenmei());
		eneteitou.setMokuteki(neteitou.getMokuteki());
		eneteitou.setGenin(neteitou.getGenin());
		eneteitou.setSaikengaku(neteitou.getSaikengaku());
		eneteitou.setSaikenhanni(neteitou.getSaikenhanni());
		if(neteitou.getKakuteikijitsu() == null || neteitou.getKakuteikijitsu().isEmpty()) {

		} else {
			eneteitou.setKakuteikijitsu(neteitou.getKakuteikijitsu());
		}
		eneteitou.setTempsyorui(neteitou.getTempsyorui());
		eneteitou.setDate(neteitou.getDate());
		eneteitou.setTokisyo(neteitou.getTokisyo());
		eneteitou.setKazeiGoukei(neteitou.getKazeiGoukei());
		eneteitou.setToumenGoukei(neteitou.getToumenGoukei());
		eneteitou.setJyobun(neteitou.getJyobun());

		List<Saimusya> saimusyaList = eneteitou.getSaimusyaList();
		if(!saimusyaList.isEmpty()) {
			for(int i = 0; i < saimusyas.length; i++) {
				Saimusya saimusya = saimusyaList.get(i);
				Customer customer = customerService.find(saimusyas[i]);
				saimusya.setCustomer(customer);
				saimusya.setAddr(saimusyaaddrs[i]);
				saimusyaService.saveSaimusya(saimusya);
				saimusyaList.add(saimusya);
			}
		}
		eneteitou.setSaimusyaList(saimusyaList);

		List<Kenrisya> neteitoukensyaList = eneteitou.getNeteitoukensyaList();
		if(!neteitoukensyaList.isEmpty()) {
			for(int i = 0; i < customers.length; i++) {
				Kenrisya neteitoukensya = neteitoukensyaList.get(i);
				Customer customer = customerService.find(customers[i]);
				neteitoukensya.setCustomer(customer);
				if(addrs[i] != null || !(addrs[i].isEmpty())) {
					neteitoukensya.setAddr(addrs[i]);
				}
				if(daihyos[i] != null || !(daihyos[i].isEmpty())){
					neteitoukensya.setDaihyo(daihyos[i]);
				}
				if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
					neteitoukensya.setMochibun(mochibuns[i]);
				}
				if(shitens[i] != null || !(shitens[i].isEmpty())) {
					neteitoukensya.setShiten(shitens[i]);
				}
				kenrisyaService.saveKenrisya(neteitoukensya);
				neteitoukensyaList.add(neteitoukensya);
			}
		}
		eneteitou.setNeteitoukensyaList(neteitoukensyaList);

		List<Gimusya> gimusyaList = gimusyaService.setGimusyaList(eneteitou.getGimusyaList(),
				gimusyas, gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		eneteitou.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		eneteitou.setShinseiBukkenList(bukkenList);

		neteitouService.saveNeteitou(eneteitou);

		return new ModelAndView("redirect:/soft/neteitou/{id}");
	}

}
