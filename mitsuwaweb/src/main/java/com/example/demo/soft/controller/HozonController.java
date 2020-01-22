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

import com.example.demo.entity.customer.Customer;
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
	ShinseiBukkenService bukkenService;

	@Autowired
	TempsyoruiService tempService;

	@Autowired
	KenrisyaService kenrisyaService;

	/**
	 * ２項保存一覧
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
		mav.addObject("title", "２項保存一覧");
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * ２項保存新規作成
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
		mav.addObject("title", "２項保存データ新規作成");
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
			@RequestParam("tokisyo") Integer tokisyo
			) {

		List<Kenrisya> syoyusyaList = new ArrayList<Kenrisya>();
		for(int i = 0; i < customers.length; i++) {
			Kenrisya kenrisya = new Kenrisya();
			Customer customer = customerService.find(customers[i]);
			kenrisya.setCustomer(customer);
			kenrisya.setAddr();
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
		return new ModelAndView("redirect:/soft/hozon");
	}

	/**
	 * ２項保存詳細
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
		mav.addObject("title", "２項保存詳細");
		mav.addObject("hozon", hozonService.find(id));
		return mav;
	}

	/**
	 * ２項保存編集
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
		mav.addObject("title", "２項保存編集");
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
			@RequestParam("shinseiBukken") Integer[] bukkens,
			@RequestParam("tokisyo") Integer tokisyo,
			ModelAndView mav
			) {

		List<Kenrisya> syoyusyaList = new ArrayList<Kenrisya>();
		for(int i = 0; i < customers.length; i++) {
			Kenrisya kenrisya = new Kenrisya();
			Customer customer = customerService.find(customers[i]);
			kenrisya.setCustomer(customer);
			kenrisya.setAddr();
			if(mochibuns[i] != null || !(mochibuns[i].isEmpty())) {
				kenrisya.setMochibun(mochibuns[i]);
			}
			kenrisyaService.saveKenrisya(kenrisya);
			syoyusyaList.add(kenrisya);
		}
		hozon.setSyoyusya(syoyusyaList);

		List<ShinseiBukken> bukkenList = new ArrayList<ShinseiBukken>();
		for(Integer bukkenid : bukkens) {
			ShinseiBukken bukken = bukkenService.find(bukkenid);
			bukkenList.add(bukken);
		}
		hozon.setShinseiBukkenList(bukkenList);
		hozonService.saveHozon(hozon);

		return new ModelAndView("redirect:/soft/hozon/{id}");
	}

	/**
	 * ２項保存削除
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

	@GetMapping("/soft/hozon/test")
	public  ModelAndView ajaxtest(
			ModelAndView mav) {

		mav.setViewName("layout");
		mav.addObject("contents", "hozon/ajax::hozon_contents");
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@GetMapping("/soft/hozon/select")
	@ResponseBody
	public String getSelectData(@RequestParam("id") Integer id) {
		System.out.println(id);
		StringBuilder sb = new StringBuilder("<option value=\"nothing\">-</option>");
		List<String> selectDataList = new ArrayList<String>();
		String addr = customerService.find(id).getHonninKakuninList().get(0).getAddressList().get(0).getAddr();
		selectDataList.add(addr);
        selectDataList.stream()
            .map(value -> String.format("<option value=\"%s\">%s</option>", value, value))
            .forEach(option -> sb.append(option));

        return sb.toString();
	}
}