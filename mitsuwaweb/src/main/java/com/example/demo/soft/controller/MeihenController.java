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
import com.example.demo.soft.entity.Meihen;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.service.GimusyaService;
import com.example.demo.soft.service.MeihenService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class MeihenController {

	@Autowired
	MeihenService meihenService;

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
	GimusyaService gimusyaService;

	/**
	 * 名変一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Meihen> list = meihenService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/index::meihen_contents");
		mav.addObject("title", "名変一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 名変新規作成画面
	 * @param meihen
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen/new")
	public ModelAndView meihenNew(
			@ModelAttribute Meihen meihen,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/new::meihen_contents");
		mav.addObject("title", "名変新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/meihen/new")
	public ModelAndView create(
			@ModelAttribute Meihen meihen,
			@RequestParam(name = "tokisyo") Integer tokisyo,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos
			) {

		List<Gimusya> shinseininList = gimusyaService.newMeihenShinseininList(new ArrayList<Gimusya>(),
				customers, addrs, daihyos);
		meihen.setShinseininList(shinseininList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		meihen.setShinseiBukkenList(bukkenList);

		meihenService.saveMeihen(meihen);

		return new ModelAndView("redirect:/soft/meihen/" + meihen.getId());
	}

	/**
	 * 名変詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/show::meihen_contents");
		mav.addObject("title", "名変詳細");
		mav.addObject("meihen", meihenService.find(id));
		return mav;
	}

	/**
	 * 名変編集
	 * @param id
	 * @param meihen
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			@ModelAttribute Meihen meihen,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/edit::meihen_contents");
		mav.addObject("title", "名変編集");
		mav.addObject("meihen", meihenService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/meihen/{id}/edit")
	public ModelAndView update(
		@PathVariable Integer id,
		@ModelAttribute("Meihen") Meihen meihen,
		@RequestParam(name="shinseiBukken", required=false) Integer[] bukkens,
		@RequestParam("customer") Integer[] customers,
		@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
		@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos
			) {

		Meihen emeihen = meihenService.find(id);
		emeihen.setKenmei(meihen.getKenmei());
		emeihen.setMokuteki(meihen.getMokuteki());
		emeihen.setGenin(meihen.getGenin());
		emeihen.setHenkojiko(meihen.getHenkojiko());
		emeihen.setTempsyorui(meihen.getTempsyorui());
		emeihen.setDate(meihen.getDate());
		emeihen.setTokisyo(meihen.getTokisyo());
		emeihen.setToumenGoukei(meihen.getToumenGoukei());
		emeihen.setJyobun(meihen.getJyobun());

		List<Gimusya> shinseininList = gimusyaService.editMeihenShinseininList(emeihen.getShinseininList(),
				customers, addrs, daihyos);
		emeihen.setShinseininList(shinseininList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		emeihen.setShinseiBukkenList(bukkenList);

		meihenService.saveMeihen(emeihen);

		return new ModelAndView("redirect:/soft/meihen/{id}");
	}

	/**
	 * 名変抹消
	 * @param id
	 * @return
	 */
	@PostMapping("/soft/meihen/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id
			) {
		meihenService.delete(id);
		return new ModelAndView("redirect:/soft/meihen");
	}

	/**
	 * 名変　申請人追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen/{id}/shinseininnew")
	public ModelAndView gimusyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/gimusyanew::shinseinin_contents");
		mav.addObject("title", "申請人追加");
		mav.addObject("meihen", meihenService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/meihen/{id}/shinseininnew")
	public ModelAndView shinseinincreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo
			) {
		Meihen meihen = meihenService.find(id);
		List<Gimusya> shinseininList = meihen.getShinseininList();
		Gimusya shinseinin = new Gimusya();
		if(addr != null) {
			shinseinin.setAddr(addr);
		}
		shinseinin.setCustomer(customer);
		if(daihyo != null){
			shinseinin.setDaihyo(daihyo);
		}

		gimusyaService.saveGimusya(shinseinin);
		shinseininList.add(shinseinin);
		meihen.setShinseininList(shinseininList);
		meihenService.saveMeihen(meihen);
		return new ModelAndView("redirect:/soft/meihen/{id}");
	}

	/**
	 * 名変　申請人削除
	 * @param id
	 * @param kid
	 * @return
	 */
	@PostMapping("/soft/meihen/{id}/shinseinindelete/{gid}")
	public ModelAndView gimusyadelete(
			@PathVariable Integer id,
			@PathVariable Integer gid
			) {
		Meihen meihen = meihenService.find(id);
		List<Gimusya> shinseininList = meihen.getShinseininList();
		Gimusya shinseinin = gimusyaService.find(gid);
		shinseininList.remove(shinseinin);
		meihen.setShinseininList(shinseininList);
		meihenService.saveMeihen(meihen);
		gimusyaService.delete(gid);

		return new ModelAndView("redirect:/soft/meihen/{id}");

	}

	/**
	 * 物件追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/meihen/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("meihen", meihenService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/meihen/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid
			) {
		Meihen meihen = meihenService.find(id);
		List<ShinseiBukken> bukkens = meihen.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		meihen.setShinseiBukkenList(bukkens);
		meihenService.saveMeihen(meihen);

		return new ModelAndView("redirect:/soft/meihen/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/meihen/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid
			) {
		Meihen meihen = meihenService.find(sid);
		List<ShinseiBukken> bukkens = meihen.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		meihen.setShinseiBukkenList(bukkens);
		meihenService.saveMeihen(meihen);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/meihen/{sid}");
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
	@GetMapping("/soft/meihen/{id}/create")
	public ModelAndView meihen(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Meihen meihen = meihenService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/show::meihen_contents");
		String message = meihenService.xmlFileGet("HM0501202320001",meihen);
		mav.addObject("createMessage", message);
		mav.addObject("title", "名変作成");
		mav.addObject("meihen", meihen);
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
	@GetMapping("/soft/meihen/{id}/qrcreate")
	public ModelAndView qrmeihen(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Meihen meihen = meihenService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "meihen/show::meihen_contents");
		String message = meihenService.xmlFileGet("HM0508202320001",meihen);
		mav.addObject("createMessage", message);
		mav.addObject("title", "QR名変作成");
		mav.addObject("meihen", meihen);
		return mav;
	}

}
