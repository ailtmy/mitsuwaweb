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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.example.demo.entity.customer.Company;
import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.customer.Daihyo;
import com.example.demo.entity.honninkakunin.CustomerAddress;
import com.example.demo.entity.honninkakunin.HonninKakunin;
import com.example.demo.service.customer.CompanyService;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.service.HozonService;
import com.example.demo.soft.service.KenrisyaService;
import com.example.demo.soft.service.ShinseiBukkenService;
import com.example.demo.soft.service.TempsyoruiService;
import com.example.demo.soft.service.TokisyoService;

@Controller
public class HozonController {

	@Autowired
	HozonService hozonService;

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
	 * 保存一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon")
	public ModelAndView index(
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav
			) {
		Page<Hozon> list = hozonService.getAll(pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/index::hozon_contents");
		mav.addObject("title", "保存一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 保存新規作成
	 * @param hozon
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon/new")
	public ModelAndView hozonNew(
			@ModelAttribute Hozon hozon,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/new::hozon_contents");
		mav.addObject("title", "保存データ新規作成");
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/hozon/new")
	public ModelAndView hozonCreate(
			ModelAndView mav,
			@ModelAttribute Hozon hozon,
			@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
			@RequestParam("customer") Integer[] customers,
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("tokisyo") Integer tokisyo,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs,
			@RequestParam(name = "daihyo", defaultValue=" ") String[] daihyos
			) {

		List<Kenrisya> syoyusyaList = new ArrayList<Kenrisya>();
		for(int i = 0; i < customers.length; i++) {
			Kenrisya kenrisya = new Kenrisya();
			Customer customer = customerService.find(customers[i]);
			kenrisya.setCustomer(customer);
			if(addrs[i] != null || !(addrs[i].isEmpty())) {
				kenrisya.setAddr(addrs[i]);
			}
			if(daihyos[i] != null || !(daihyos[i].isEmpty())) {
				kenrisya.setDaihyo(daihyos[i]);
			}
			if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
				kenrisya.setMochibun(mochibuns[i]);
			}
			kenrisyaService.saveKenrisya(kenrisya);
			syoyusyaList.add(kenrisya);
		}
		hozon.setSyoyusya(syoyusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer id : bukkens) {
			ShinseiBukken bukken = bukkenService.find(id);
			bukkenList.add(bukken);
		}
		hozon.setShinseiBukkenList(bukkenList);

		hozonService.saveHozon(hozon);
		return new ModelAndView("redirect:/soft/hozon/" + hozon.getId());
	}

	/**
	 * 保存詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/show::hozon_contents");
		mav.addObject("title", "保存詳細");
		mav.addObject("hozon", hozonService.find(id));
		return mav;
	}

	/**
	 * 保存編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/edit::hozon_contents");
		mav.addObject("title", "保存編集");
		mav.addObject("hozon", hozonService.find(id));
		mav.addObject("tokisyo", tokisyoService.findAll());
		mav.addObject("customer", customerService.allget());
		mav.addObject("shinseiBukken", bukkenService.allget());
		mav.addObject("tempsyorui", tempService.allget());
		return mav;
	}

	@PostMapping("/soft/hozon/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute("Hozon") Hozon hozon,
			@RequestParam(name="mochibun", defaultValue=" ") String[] mochibuns,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "shinseiBukken", required = false) Integer[] bukkens,
			@RequestParam("tokisyo") Integer tokisyo,
			@RequestParam("addr") String[] addrs,
			@RequestParam("daihyo") String[] daihyos,
			ModelAndView mav
			) {

		Hozon edithozon = hozonService.find(id);
		edithozon.setDate(hozon.getDate());
		edithozon.setGenin(hozon.getGenin());
		edithozon.setJyobun(hozon.getJyobun());
		edithozon.setKazeiGoukei(hozon.getKazeiGoukei());
		edithozon.setKazeiUchiwake(hozon.getKazeiUchiwake());
		edithozon.setKenmei(hozon.getKenmei());
		edithozon.setTempsyorui(hozon.getTempsyorui());
		edithozon.setTokisyo(hozon.getTokisyo());
		edithozon.setToumenGoukei(hozon.getToumenGoukei());
		edithozon.setToumenUchiwake(hozon.getToumenUchiwake());

		List<Kenrisya> syoyusyaList = edithozon.getSyoyusya();
		if(!syoyusyaList.isEmpty()) {
			for(int i = 0; i < customers.length; i++) {
				Kenrisya kenrisya = syoyusyaList.get(i);
				Customer customer = customerService.find(customers[i]);
				kenrisya.setCustomer(customer);
				kenrisya.setAddr(addrs[i]);
				kenrisya.setDaihyo(daihyos[i]);
				if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
					kenrisya.setMochibun(mochibuns[i]);
				}
				kenrisyaService.saveKenrisya(kenrisya);
				syoyusyaList.add(kenrisya);
			}
		}
		edithozon.setSyoyusya(syoyusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		edithozon.setShinseiBukkenList(bukkenList);

		hozonService.saveHozon(edithozon);

		return new ModelAndView("redirect:/soft/hozon/{id}");
	}

	/**
	 * 保存削除
	 * @param id
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/hozon/{id}/delete")
	public ModelAndView delete(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		hozonService.delete(id);
		return new ModelAndView("redirect:/soft/hozon");
	}

	/**
	 * 所有者追加
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/shinseisyo/{id}/kenrisyanew")
	public ModelAndView syoyusyanew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "kenrisya/kenrisyanew::kenrisya_contents");
		mav.addObject("title", "所有者追加");
		mav.addObject("kenrisya", hozonService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/soft/shinseisyo/{id}/kenrisyanew")
	public ModelAndView syosyusyacreate(
			@PathVariable Integer id,
			@RequestParam(name = "addr", required=false) String addr,
			@RequestParam(name = "mochibun", required=false, defaultValue=" ") String mochibun,
			@RequestParam(name = "customer") Customer customer,
			@RequestParam(name = "daihyo", required=false) String daihyo,
			ModelAndView mav
			) {
		Hozon hozon = hozonService.find(id);
		List<Kenrisya> syoyusya = hozon.getSyoyusya();
		Kenrisya kenrisya = new Kenrisya();
		kenrisya.setAddr(addr);
		kenrisya.setCustomer(customer);
		kenrisya.setDaihyo(daihyo);
		kenrisya.setMochibun(mochibun);
		kenrisyaService.saveKenrisya(kenrisya);
		syoyusya.add(kenrisya);
		hozon.setSyoyusya(syoyusya);
		hozonService.saveHozon(hozon);

		return new ModelAndView("redirect:/soft/hozon/{id}");
	}

	/**
	 * 所有者削除
	 * @param sid
	 * @param kid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/shinseisyo/{sid}/kenrisyadelete/{kid}")
	public ModelAndView kenrisyadelete(
			@PathVariable Integer sid,
			@PathVariable Integer kid,
			ModelAndView mav
			) {
		Hozon hozon = hozonService.find(sid);
		List<Kenrisya> kenrisyas = hozon.getSyoyusya();
		Kenrisya kenrisya = kenrisyaService.find(kid);
		kenrisyas.remove(kenrisya);
		hozon.setSyoyusya(kenrisyas);
		hozonService.saveHozon(hozon);
		kenrisyaService.delete(kid);

		return new ModelAndView("redirect:/soft/hozon/{sid}");
	}

	@GetMapping("/soft/shinseibukken/{id}/bukkennew")
	public ModelAndView bukkennew(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "bukken/bukkennew::bukken_contents");
		mav.addObject("title", "物件追加");
		mav.addObject("shinseisyo", hozonService.find(id));
		mav.addObject("shinseiBukken", bukkenService.allget());
		return mav;
	}

	@PostMapping("/soft/shinseibukken/{id}/bukkennew")
	public ModelAndView bukkencreate(
			@PathVariable Integer id,
			@RequestParam(name = "bukken") Integer bid,
			ModelAndView mav
			) {
		Hozon hozon = hozonService.find(id);
		List<ShinseiBukken> bukkens = hozon.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.add(bukken);
		hozon.setShinseiBukkenList(bukkens);
		hozonService.saveHozon(hozon);

		return new ModelAndView("redirect:/soft/hozon/{id}");
	}

	/**
	 * 申請書物件削除
	 * @param sid
	 * @param bid
	 * @param mav
	 * @return
	 */
	@PostMapping("/soft/shinseibukken/{sid}/bukkendelete/{bid}")
	public ModelAndView bukkendelete(
			@PathVariable Integer sid,
			@PathVariable Integer bid,
			ModelAndView mav
			) {
		Hozon hozon = hozonService.find(sid);
		List<ShinseiBukken> bukkens = hozon.getShinseiBukkenList();
		ShinseiBukken bukken = bukkenService.find(bid);
		bukkens.remove(bukken);
		hozon.setShinseiBukkenList(bukkens);
		hozonService.saveHozon(hozon);
		bukkenService.delete(bid);

		return new ModelAndView("redirect:/soft/hozon/{sid}");
	}


	/**
	 * ２項保存オンライン特例申請ファイル作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/hozon/{id}/create")
	public ModelAndView hozon(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Hozon hozon = hozonService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/show::hozon_contents");
		String message = hozonService.xmlFileGet("HM0501200320001", hozon);
		mav.addObject("createMessage", message);
		mav.addObject("title", "２項保存作成");
		mav.addObject("hozon", hozon);
		return mav;
	}

	/**
	 * ２項保存QR申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/hozon/{id}/qrcreate")
	public ModelAndView qrhozon(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Hozon hozon = hozonService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/show::hozon_contents");
		String message = hozonService.xmlFileGet("HM0508200320001", hozon);
		mav.addObject("createMessage", message);
		mav.addObject("title", "２項保存QR作成");
		mav.addObject("hozon", hozon);
		return mav;
	}

	/**
	 * １項保存オンライン特例申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/hozon/{id}/hozoncreate")
	public ModelAndView hozonone(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Hozon hozon = hozonService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/show::hozon_contents");
		String message = hozonService.hozonxmlFileGet("HM0501200220001", hozon);
		mav.addObject("createMessage", message);
		mav.addObject("title", "１項保存作成");
		mav.addObject("hozon", hozon);
		return mav;
	}

	/**
	 * １項保存QR申請作成
	 * @param id
	 * @param mav
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@GetMapping("/soft/hozon/{id}/hozonqrcreate")
	public ModelAndView qrhozonone(
			@PathVariable Integer id,
			ModelAndView mav
			) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Hozon hozon = hozonService.find(id);
		mav.setViewName("layout");
		mav.addObject("contents", "hozon/show::hozon_contents");
		String message = hozonService.hozonxmlFileGet("HM0508200220001", hozon);
		mav.addObject("createMessage", message);
		mav.addObject("title", "１項保存QR作成");
		mav.addObject("hozon", hozon);
		return mav;
	}


	/**
	 * ajax テスト
	 * @param mav
	 * @return
	 */
	@GetMapping("/soft/hozon/test")
	public  ModelAndView ajaxtest(
			ModelAndView mav) {

		mav.setViewName("layout");
		mav.addObject("contents", "hozon/ajax::hozon_contents");
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	/**
	 * 権利者住所選択Ajax
	 * @param id
	 * @return
	 */
	@GetMapping("/soft/hozon/addr")
	@ResponseBody
	public String getAddressData(Integer id) {
		StringBuilder sb = new StringBuilder("<option value=\"\"></option>");
		List<String> selectAddressList = new ArrayList<String>();
		List<HonninKakunin> honninkakuninList = customerService.find(id).getHonninKakuninList();
		for(HonninKakunin honninkakunin : honninkakuninList) {
			List<CustomerAddress> addressList = honninkakunin.getAddressList();
			for(CustomerAddress address : addressList) {
				String addr = address.getAddr();
				selectAddressList.add(addr);
			}
		}

        selectAddressList.stream()
            .map(value -> String.format("<option value=\"%s\">%s</option>", value, value))
            .forEach(option -> sb.append(option));

        return sb.toString();
	}

	/**
	 * 代表者選択Ajax
	 * @param id
	 * @return
	 */
	@GetMapping("/soft/hozon/daihyo")
	@ResponseBody
	public String getDaihyoData(Integer id) {
		StringBuilder sb = new StringBuilder("<option value=\"\"></option>");
		List<String> selectDaihyoList = new ArrayList<String>();
		Customer customer = customerService.find(id);
		if(customer instanceof Company) {
			Company company = (Company) customer;
			List<Daihyo> daihyoList = company.getDaihyosya();
			for(Daihyo daihyo : daihyoList) {
				String daitoriName = daihyo.getKatagaki();
				daitoriName += "/" + daihyo.getDaitori().getName();
				selectDaihyoList.add(daitoriName);
			}
		}
		selectDaihyoList.stream()
		.map(value -> String.format("<option value=\"%s\">%s</option>", value, value))
		.forEach(option -> sb.append(option));

		return sb.toString();
	}
}