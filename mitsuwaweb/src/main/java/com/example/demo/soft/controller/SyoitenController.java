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
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Syoiten;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.SyoitenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class SyoitenController {

	@Autowired
	SyoitenService syoitenService;

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
	 * 所有権移転一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Syoiten> list = syoitenService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/index::syoiten_contents");
		mav.addObject("title", "所有権移転一覧");
		mav.addObject("list", list);
		return mav;

	}

	/**
	 * 所有権移転新規作成
	 * @param syoiten
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/new")
	public ModelAndView syoitenNew(
		@ModelAttribute Syoiten syoiten,
		ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/new::syoiten_contents");
		mav.addObject("title", "所有権移転新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/syoiten/new")
	public ModelAndView create(
			@ModelAttribute Syoiten syoiten,
			@RequestParam(name = "tokisyo") Integer tokisyo,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("mochibun") String[] mochibuns,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos,
			@RequestParam("gimusya") Integer[] gimusyas,
			@RequestParam(name = "gimusyaaddr", defaultValue=" ") String[] gimusyaaddrs,
			@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
			@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
			@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus
			) {

		List<Kenrisya> kenrisyaList = kenrisyaService.newSyoyuKenrisya(new ArrayList<Kenrisya>(), customers,
				mochibuns, addrs, daihyos);
		syoiten.setKenrisyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.newMassyoGimusyaList(new ArrayList<Gimusya>(),
				gimusyas, gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		syoiten.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		syoiten.setShinseiBukkenList(bukkenList);

		syoitenService.saveSyoiten(syoiten);

		return new ModelAndView("redirect:/soft/syoiten/" + syoiten.getId());
	}


	/**
	 * 所有権移転詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/show::syoiten_contents");
		mav.addObject("title", "所有権移転詳細");
		mav.addObject("syoiten", syoitenService.find(id));
		return mav;
	}

	/**
	 * 所有権移転編集
	 * @param id
	 * @param syoiten
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			@ModelAttribute Syoiten syoiten,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/edit::syoiten_contents");
		mav.addObject("title", "所有権移転編集");
		mav.addObject("syoiten", syoitenService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/syoiten/{id}/edit")
	public ModelAndView update(
		@PathVariable Integer id,
		@ModelAttribute("Syoiten") Syoiten syoiten,
		@RequestParam(name="shinseiBukken", required=false) Integer[] bukkens,
		@RequestParam("customer") Integer[] customers,
		@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
		@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
		@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos,
		@RequestParam("gimusya") Integer[] gimusyas,
		@RequestParam(name = "gimusyaaddr", defaultValue=" ") String[] gimusyaaddrs,
		@RequestParam(name = "gimusyadaihyo", defaultValue=" ") String[] gimusyadaihyos,
		@RequestParam(name = "shikibetsuumu", defaultValue=" ") String[] shikibetsuumus,
		@RequestParam(name = "shikibetsuriyu", defaultValue=" ") String[] shikibetsuriyus
			) {

		Syoiten esyoiten = syoitenService.find(id);
		esyoiten.setKenmei(syoiten.getKenmei());
		esyoiten.setMokuteki(syoiten.getMokuteki());
		esyoiten.setGenin(syoiten.getGenin());
		esyoiten.setTempsyorui(syoiten.getTempsyorui());
		esyoiten.setDate(syoiten.getDate());
		esyoiten.setTokisyo(syoiten.getTokisyo());
		esyoiten.setKazeiGoukei(syoiten.getKazeiGoukei());
		esyoiten.setKazeiUchiwake(syoiten.getKazeiUchiwake());
		esyoiten.setToumenGoukei(syoiten.getToumenGoukei());
		esyoiten.setToumenUchiwake(syoiten.getToumenUchiwake());
		esyoiten.setJyobun(syoiten.getJyobun());

		List<Kenrisya> kenrisyaList = kenrisyaService.editSyoyuKenrisya(esyoiten.getKenrisyaList(),
				customers, mochibuns, addrs, daihyos);
		esyoiten.setKenrisyaList(kenrisyaList);

		List<Gimusya> gimusyaList = gimusyaService.editMassyoGimusyaList(esyoiten.getGimusyaList(), gimusyas,
				gimusyaaddrs, gimusyadaihyos, shikibetsuumus, shikibetsuriyus);
		esyoiten.setGimusyaList(gimusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		esyoiten.setShinseiBukkenList(bukkenList);

		syoitenService.saveSyoiten(esyoiten);

		return new ModelAndView("redirect:/soft/syoiten/{id}");
	}

	/**
	 * 所有権移転抹消
	 * @param id
	 * @return
	 */
	@PostMapping("/soft/syoiten/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id
			) {
		syoitenService.delete(id);
		return new ModelAndView("redirect:/soft/syoiten");
	}

	/**
	 * 所有権移転　権利者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/{id}/kenrisyanew")
	public ModelAndView kenrisyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "権利者追加");
		mav.addObject("syoiten", syoitenService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/syoiten/{id}/kenrisyanew")
	public ModelAndView kenrisyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "mochibun", required=false) String mochibun,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Syoiten syoiten = syoitenService.find(id);
		List<Kenrisya> kenrisyas = syoiten.getKenrisyaList();
		Kenrisya kenrisya = new Kenrisya();
		if(mochibun != null) {
			kenrisya.setMochibun(mochibun);
		}
		if(addr != null) {
			kenrisya.setAddr(addr);
		}
		kenrisya.setCustomer(customer);
		if(daihyo != null) {
			kenrisya.setDaihyo(daihyo);
		}
		kenrisyaService.saveKenrisya(kenrisya);
		kenrisyas.add(kenrisya);
		syoiten.setKenrisyaList(kenrisyas);
		syoitenService.saveSyoiten(syoiten);
		return new ModelAndView("redirect:/soft/syoiten/{id}");
	}

	/**
	 * 所有権移転　権利者削除
	 * @param id
	 * @param kid
	 * @return
	 */
	@PostMapping("/soft/syoiten/{id}/kenrisyadelete/{kid}")
	public ModelAndView kenrisyadelete(
			@PathVariable Integer id,
			@PathVariable Integer kid
			) {
		Syoiten syoiten = syoitenService.find(id);
		List<Kenrisya> kenrisyas = syoiten.getKenrisyaList();
		Kenrisya kenrisya = kenrisyaService.find(kid);
		kenrisyas.remove(kenrisya);
		syoiten.setKenrisyaList(kenrisyas);
		syoitenService.saveSyoiten(syoiten);
		kenrisyaService.delete(kid);

		return new ModelAndView("redirect:/soft/syoiten/{id}");
	}

	/**
	 * 所有権移転　義務者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/{id}/gimusyanew")
	public ModelAndView gimusyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/gimusyanew::gimusya_contents");
		mav.addObject("title", "義務者追加");
		mav.addObject("syoiten", syoitenService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/syoiten/{id}/gimusyanew")
	public ModelAndView gimusyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo,
			@RequestParam(name = "shikibetsuumu", required=false) String shikibetsuumu,
			@RequestParam(name = "shikibetsuriyu", required=false) String shikibetsuriyu
			) {
		Syoiten syoiten = syoitenService.find(id);
		List<Gimusya> gimusyas = syoiten.getGimusyaList();
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
		syoiten.setGimusyaList(gimusyas);
		syoitenService.saveSyoiten(syoiten);
		return new ModelAndView("redirect:/soft/syoiten/{id}");
	}

	/**
	 * 所有権移転　義務者抹消
	 * @param id
	 * @param gid
	 * @return
	 */
	@PostMapping("/soft/syoiten/{id}/gimusyadelete/{gid}")
	public ModelAndView gimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer gid
			) {
		Syoiten syoiten = syoitenService.find(id);
		List<Gimusya> gimusyas = syoiten.getGimusyaList();
		Gimusya gimusya = gimusyaService.find(gid);
		gimusyas.remove(gimusya);
		syoiten.setGimusyaList(gimusyas);
		syoitenService.saveSyoiten(syoiten);
		gimusyaService.delete(gid);

		return new ModelAndView("redirect:/soft/syoiten/{id}");

	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/syoiten/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("syoiten", syoitenService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/syoiten/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid
			) {
		Syoiten syoiten = syoitenService.find(id);
		List<ShinseiBukken> bukkens = syoiten.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		syoiten.setShinseiBukkenList(bukkens);
		syoitenService.saveSyoiten(syoiten);

		return new ModelAndView("redirect:/soft/syoiten/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/syoiten/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid
			) {
		Syoiten syoiten = syoitenService.find(sid);
		List<ShinseiBukken> bukkens = syoiten.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		syoiten.setShinseiBukkenList(bukkens);
		syoitenService.saveSyoiten(syoiten);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/syoiten/{sid}");
	}

	/**
	 * 所有権移転　特例オンライン申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/syoiten/{id}/create")
	public ModelAndView syoiten(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Syoiten syoiten = syoitenService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/show::syoiten_contents");
		String message = syoitenService.xmlFileGet("HM0501200420001",syoiten);
		mav.addObject("createMessage", message);
		mav.addObject("title", "所有権移転作成");
		mav.addObject("syoiten", syoiten);
		return mav;
	}

	/**
	 * 所有権移転　QR書面申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/syoiten/{id}/qrcreate")
	public ModelAndView qrsyoiten(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Syoiten syoiten = syoitenService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "syoiten/show::syoiten_contents");
		String message = syoitenService.xmlFileGet("HM0508200420001",syoiten);
		mav.addObject("createMessage", message);
		mav.addObject("title", "QR所有権移転作成");
		mav.addObject("syoiten", syoiten);
		return mav;
	}

}
