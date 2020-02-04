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
				Kenrisya neteitousya = neteitoukensyaList.get(i);
				Customer customer = customerService.find(customers[i]);
				neteitousya.setCustomer(customer);
				if(addrs[i] != null || !(addrs[i].isEmpty())) {
					neteitousya.setAddr(addrs[i]);
				}
				if(daihyos[i] != null || !(daihyos[i].isEmpty())){
					neteitousya.setDaihyo(daihyos[i]);
				}
				if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
					neteitousya.setMochibun(mochibuns[i]);
				}
				if(shitens[i] != null || !(shitens[i].isEmpty())) {
					neteitousya.setShiten(shitens[i]);
				}
				kenrisyaService.saveKenrisya(neteitousya);
				neteitoukensyaList.add(neteitousya);
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

	/**
	 * 根抵当権　削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/neteitou/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		neteitouService.delete(id);
		return new ModelAndView("redirect:/soft/neteitou");
	}

	/**
	 * 債務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}/saimusyanew")
	public ModelAndView saimusyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/saimusyanew::saimusya_contents");
		mav.addObject("title", "債務者追加");
		mav.addObject("neteitou", neteitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/neteitou/{id}/saimusyanew")
	public ModelAndView saimusaycreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Saimusya> saimusyas = neteitou.getSaimusyaList();
		Saimusya saimusya = new Saimusya();
		saimusya.setAddr(addr);
		saimusya.setCustomer(customer);
		saimusyaService.saveSaimusya(saimusya);
		saimusyas.add(saimusya);
		neteitou.setSaimusyaList(saimusyas);
		neteitouService.saveNeteitou(neteitou);

		return new ModelAndView("redirect:/soft/neteitou/{id}");
	}

	/**
	 * 債務者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/neteitou/{id}/saimusyadelete/{sid}")
	public ModelAndView saimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Saimusya> saimusyas = neteitou.getSaimusyaList();
		Saimusya saimusya = saimusyaService.find(sid);
		saimusyas.remove(saimusya);
		neteitou.setSaimusyaList(saimusyas);
		neteitouService.saveNeteitou(neteitou);
		saimusyaService.delete(sid);

		return new ModelAndView("redirect:/soft/neteitou/{id}");

	}

	/**
	 * 根抵当権設定　根抵当権者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}/kenrisyanew")
	public ModelAndView kenrisyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "根抵当権者追加");
		mav.addObject("neteitou", neteitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/neteitou/{id}/kenrisyanew")
	public ModelAndView kenrisyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "mochibun", required=false, defaultValue=" ") String mochibun,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "shiten", required=false) String shiten,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Kenrisya> kenrisyas = neteitou.getNeteitoukensyaList();
		Kenrisya kenrisya = new Kenrisya();
		kenrisya.setAddr(addr);
		kenrisya.setMochibun(mochibun);
		kenrisya.setCustomer(customer);
		kenrisya.setShiten(shiten);
		kenrisya.setDaihyo(daihyo);
		kenrisyaService.saveKenrisya(kenrisya);
		kenrisyas.add(kenrisya);
		neteitou.setNeteitoukensyaList(kenrisyas);
		neteitouService.saveNeteitou(neteitou);

		return new ModelAndView("redirect:/soft/neteitou/{id}");
	}

	/**
	 * 根抵当権者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/neteitou/{id}/kenrisyadelete/{sid}")
	public ModelAndView kenrisyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Kenrisya> kenrisyas = neteitou.getNeteitoukensyaList();
		Kenrisya kenrisya = kenrisyaService.find(sid);
		kenrisyas.remove(kenrisya);
		neteitou.setNeteitoukensyaList(kenrisyas);
		neteitouService.saveNeteitou(neteitou);
		kenrisyaService.delete(sid);

		return new ModelAndView("redirect:/soft/neteitou/{id}");

	}

	/**
	 * 根抵当権設定　義務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}/gimusyanew")
	public ModelAndView gimusyaNew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/gimusyanew::gimusya_contents");
		mav.addObject("title", "義務者追加");
		mav.addObject("neteitou", neteitouService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/neteitou/{id}/gimusyanew")
	public ModelAndView gimusyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo,
			@RequestParam(name = "shikibetsuumu", defaultValue="有り") String shikibetsuumu,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String shikibetsuriyu
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Gimusya> gimusyas = neteitou.getGimusyaList();
		Gimusya gimusya = new Gimusya();
		gimusya.setAddr(addr);
		gimusya.setCustomer(customer);
		gimusya.setDaihyo(daihyo);
		gimusya.setShikibetsuUmu(shikibetsuumu);

		gimusya.setShikibetsuRiyu(shikibetsuriyu);

		gimusyaService.saveGimusya(gimusya);
		gimusyas.add(gimusya);
		neteitou.setGimusyaList(gimusyas);
		neteitouService.saveNeteitou(neteitou);

		return new ModelAndView("redirect:/soft/neteitou/{id}");
	}

	/**
	 * 設定者削除
	 * @param id
	 * @param sid
	 * @return
	 */
	@PostMapping("/soft/neteitou/{id}/gimusyadelete/{sid}")
	public ModelAndView gimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer sid
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<Gimusya> gimusyas = neteitou.getGimusyaList();
		Gimusya gimusya = gimusyaService.find(sid);
		gimusyas.remove(gimusya);
		neteitou.setGimusyaList(gimusyas);
		neteitouService.saveNeteitou(neteitou);
		gimusyaService.delete(sid);

		return new ModelAndView("redirect:/soft/neteitou/{id}");

	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/neteitou/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("neteitou", neteitouService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/neteitou/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid,
			ModelAndView mav
			) {
		Neteitou neteitou = neteitouService.find(id);
		List<ShinseiBukken> bukkens = neteitou.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		neteitou.setShinseiBukkenList(bukkens);
		neteitouService.saveNeteitou(neteitou);

		return new ModelAndView("redirect:/soft/neteitou/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/neteitou/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid,
			ModelAndView mav
			) {
		Neteitou neteitou = neteitouService.find(sid);
		List<ShinseiBukken> bukkens = neteitou.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		neteitou.setShinseiBukkenList(bukkens);
		neteitouService.saveNeteitou(neteitou);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/neteitou/{sid}");
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
	@GetMapping("/soft/neteitou/{id}/create")
	public ModelAndView neteitou(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Neteitou neteitou = neteitouService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/show::neteitou_contents");
		String message = neteitouService.xmlFileGet("HM0501201320001",neteitou);
		mav.addObject("createMessage", message);
		mav.addObject("title", "根抵当権設定作成");
		mav.addObject("neteitou", neteitou);
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
	@GetMapping("/soft/neteitou/{id}/qrcreate")
	public ModelAndView qrteitou(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Neteitou neteitou = neteitouService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "neteitou/show::neteitou_contents");
		String message = neteitouService.xmlFileGet("HM0508201320001", neteitou);
		mav.addObject("createMessage", message);
		mav.addObject("title", "根抵当権設定QR作成");
		mav.addObject("neteitou", neteitou);
		return mav;
	}

}
