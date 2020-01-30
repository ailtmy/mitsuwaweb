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
import com.example.demo.soft.entity.Massyo;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.MassyoService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class MassyoController {

	@Autowired
	MassyoService massyoService;

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
	GimusyaService gimusyaService;

	/**
	 * 抹消一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Massyo> list = massyoService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/index::massyo_contents");
		mav.addObject("title", "抹消一覧");
		mav.addObject("list", list);
		return mav;

	}

	/**
	 * 抹消新規作成画面
	 * @param massyo
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/new")
	public ModelAndView massyoNew(
			@ModelAttribute Massyo massyo,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/new::massyo_contents");
		mav.addObject("title", "抹消新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/massyo/new")
	public ModelAndView create(
			@ModelAttribute Massyo massyo,
			@RequestParam(name = "tokisyo") Integer tokisyo,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos,
			@RequestParam("gimusya") Integer[] gimusyas,
			@RequestParam(name = "gimusyaaddr", defaultValue=" ") String[] gimusyaaddrs,
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
			@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus
			) {

		List<Kenrisya> kenrisyaList = kenrisyaService.newMassyoKenrisya(new ArrayList<Kenrisya>(), customers,
				addrs, daihyos);
		massyo.setKenrisyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.newMassyoGimusyaList(new ArrayList<Gimusya>(),
				gimusyas, gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		massyo.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		massyo.setShinseiBukkenList(bukkenList);

		massyoService.saveMassyo(massyo);

		return new ModelAndView("redirect:/soft/massyo/" + massyo.getId());
	}

	/**
	 * 抹消詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/show::massyo_contents");
		mav.addObject("title", "抹消詳細");
		mav.addObject("massyo", massyoService.find(id));
		return mav;
	}

	/**
	 * 抹消編集画面
	 * @param id
	 * @param massyo
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			@ModelAttribute Massyo massyo,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/edit::massyo_contents");
		mav.addObject("title", "抵当権抹消編集");
		mav.addObject("massyo", massyoService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}


	@PostMapping("/soft/massyo/{id}/edit")
	public ModelAndView update(
		@PathVariable Integer id,
		@ModelAttribute("Massyo") Massyo massyo,
		@RequestParam(name="shinseiBukken", required=false) Integer[] bukkens,
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

		Massyo emassyo = massyoService.find(id);
		emassyo.setKenmei(massyo.getKenmei());
		emassyo.setMokuteki(massyo.getMokuteki());
		emassyo.setMassyoGengo(massyo.getMassyoGengo());
		emassyo.setMassyoYear(massyo.getMassyoYear());
		emassyo.setMassyoMonth(massyo.getMassyoMonth());
		emassyo.setMassyoDay(massyo.getMassyoDay());
		emassyo.setMassyoUkeban(massyo.getMassyoUkeban());
		emassyo.setGenin(massyo.getGenin());
		emassyo.setTempsyorui(massyo.getTempsyorui());
		emassyo.setDate(massyo.getDate());
		emassyo.setTokisyo(massyo.getTokisyo());
		emassyo.setToumenGoukei(massyo.getToumenGoukei());
		emassyo.setJyobun(massyo.getJyobun());

		List<Kenrisya> kenrisyaList = kenrisyaService.editMassyoKenrisya(emassyo.getKenrisyaList(),
				customers, addrs, daihyos);
		emassyo.setKenrisyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.editMassyoGimusyaList(emassyo.getGimusyaList(), gimusyas,
				gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		emassyo.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		emassyo.setShinseiBukkenList(bukkenList);

		massyoService.saveMassyo(emassyo);

		return new ModelAndView("redirect:/soft/massyo/{id}");
	}

	/**
	 * 抵当権抹消　削除
	 * @param id
	 * @return
	 */
	@PostMapping("/soft/massyo/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id
			) {
		massyoService.delete(id);
		return new ModelAndView("redirect:/soft/massyo");
	}

	/**
	 * 抵当権抹消　権利者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/{id}/kenrisyanew")
	public ModelAndView kenrisyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "権利者追加");
		mav.addObject("massyo", massyoService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/massyo/{id}/kenrisyanew")
	public ModelAndView kenrisyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Massyo massyo = massyoService.find(id);
		List<Kenrisya> kenrisyas = massyo.getKenrisyaList();
		Kenrisya kenrisya = new Kenrisya();
		if(addr != null) {
			kenrisya.setAddr(addr);
		}
		kenrisya.setCustomer(customer);
		if(daihyo != null) {
			kenrisya.setDaihyo(daihyo);
		}
		kenrisyaService.saveKenrisya(kenrisya);
		kenrisyas.add(kenrisya);
		massyo.setKenrisyaList(kenrisyas);
		massyoService.saveMassyo(massyo);
		return new ModelAndView("redirect:/soft/massyo/{id}");
	}

	/**
	 * 抵当権抹消　権利者削除
	 * @param id
	 * @param kid
	 * @return
	 */
	@PostMapping("/soft/massyo/{id}/kenrisyadelete/{kid}")
	public ModelAndView kenrisyadelete(
			@PathVariable Integer id,
			@PathVariable Integer kid
			) {
		Massyo massyo = massyoService.find(id);
		List<Kenrisya> kenrisyas = massyo.getKenrisyaList();
		Kenrisya kenrisya = kenrisyaService.find(kid);
		kenrisyas.remove(kenrisya);
		massyo.setKenrisyaList(kenrisyas);
		massyoService.saveMassyo(massyo);
		kenrisyaService.delete(kid);

		return new ModelAndView("redirect:/soft/massyo/{id}");

	}

	/**
	 * 抵当権抹消　義務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/{id}/gimusyanew")
	public ModelAndView gimusyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/gimusyanew::gimusya_contents");
		mav.addObject("title", "義務者追加");
		mav.addObject("massyo", massyoService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/massyo/{id}/gimusyanew")
	public ModelAndView gimusyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo,
			@RequestParam(name = "shikibetsuumu", required=false) String shikibetsuumu,
			@RequestParam(name = "shikibetsuriyu", required=false) String shikibetsuriyu
			) {
		Massyo massyo = massyoService.find(id);
		List<Gimusya> gimusyas = massyo.getGimusyaList();
		Gimusya gimusya = new Gimusya();
		if(addr != null) {
			gimusya.setAddr(addr);
		}
		gimusya.setCustomer(customer);
		if(daihyo != null){
			gimusya.setDaihyo(daihyo);
		}
		if(shikibetsuumu != null){
			gimusya.setShikibetsuUmu(shikibetsuumu);
		}
		if(shikibetsuriyu != null){
			gimusya.setShikibetsuRiyu(shikibetsuriyu);
		}
		gimusyaService.saveGimusya(gimusya);
		gimusyas.add(gimusya);
		massyo.setGimusyaList(gimusyas);
		massyoService.saveMassyo(massyo);
		return new ModelAndView("redirect:/soft/massyo/{id}");
	}

	/**
	 * 抵当権抹消　義務者削除
	 * @param id
	 * @param kid
	 * @return
	 */
	@PostMapping("/soft/massyo/{id}/gimusyadelete/{gid}")
	public ModelAndView gimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer gid
			) {
		Massyo massyo = massyoService.find(id);
		List<Gimusya> gimusyas = massyo.getGimusyaList();
		Gimusya gimusya = gimusyaService.find(gid);
		gimusyas.remove(gimusya);
		massyo.setGimusyaList(gimusyas);
		massyoService.saveMassyo(massyo);
		gimusyaService.delete(gid);

		return new ModelAndView("redirect:/soft/massyo/{id}");

	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/massyo/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("massyo", massyoService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/massyo/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid
			) {
		Massyo massyo = massyoService.find(id);
		List<ShinseiBukken> bukkens = massyo.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		massyo.setShinseiBukkenList(bukkens);
		massyoService.saveMassyo(massyo);

		return new ModelAndView("redirect:/soft/massyo/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/massyo/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid
			) {
		Massyo massyo = massyoService.find(sid);
		List<ShinseiBukken> bukkens = massyo.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		massyo.setShinseiBukkenList(bukkens);
		massyoService.saveMassyo(massyo);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/massyo/{sid}");
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
	@GetMapping("/soft/massyo/{id}/create")
	public ModelAndView massyo(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Massyo massyo = massyoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/show::massyo_contents");
		String message = massyoService.xmlFileGet("HM0501201220001",massyo);
		mav.addObject("createMessage", message);
		mav.addObject("title", "抹消作成");
		mav.addObject("massyo", massyo);
		return mav;
	}

	/**
	 * QR書面提出用申請書作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/massyo/{id}/qrcreate")
	public ModelAndView qrmassyo(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Massyo massyo = massyoService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "massyo/show::massyo_contents");
		String message = massyoService.xmlFileGet("HM0508201220001",massyo);
		mav.addObject("createMessage", message);
		mav.addObject("title", "QR抹消作成");
		mav.addObject("massyo", massyo);
		return mav;
	}
}
