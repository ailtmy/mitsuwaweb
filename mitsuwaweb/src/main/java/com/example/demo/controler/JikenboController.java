package com.example.demo.controler;

import java.time.LocalDate;
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
import com.example.demo.entity.jikenbo.Iraisya;
import com.example.demo.entity.jikenbo.Jikenbo;
import com.example.demo.service.IraisyaService;
import com.example.demo.service.JikenboService;
import com.example.demo.service.customer.CustomerService;

@Controller
public class JikenboController {

	@Autowired
	JikenboService jikenboService;

	@Autowired
	CustomerService customerService;

	@Autowired
	IraisyaService iraisyaService;

	/**
	 * 事件簿一覧
	 * @param pageable
	 * @param mav
	 * @return
	 */
	@GetMapping("/jikenbos")
	public ModelAndView index(
			@PageableDefault(page=0, size=9)
			Pageable pageable,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "jikenbo/index::jikenbo_contents");
		mav.addObject("title", "事件簿一覧");
		Page<Jikenbo> list = jikenboService.getAll(pageable);
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 事件簿新規作成
	 * @param jikenbo
	 * @param mav
	 * @return
	 */
	@GetMapping("/jikenbos/new")
	public ModelAndView jikenboNew(
			@ModelAttribute Jikenbo jikenbo,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("title", "事件簿新規作成");
		mav.addObject("contents", "jikenbo/new::jikenbo_contents");
		mav.addObject("customer", customerService.allget());
		mav.addObject("today", LocalDate.now());
		return mav;
	}

	@PostMapping("/jikenbos/new")
	public ModelAndView create(
			@ModelAttribute Jikenbo jikenbo,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs
			) {

		List<Iraisya> iraisyaList = new ArrayList<Iraisya>();
		for(int i = 0; i < customers.length; i++) {
			Iraisya iraisya = new Iraisya();
			Customer customer = customerService.find(customers[i]);
			iraisya.setCustomer(customer);
			if(addrs[i] != null || !(addrs[i].isEmpty())) {
				iraisya.setAddr(addrs[i]);
			}
			iraisyaService.saveIraisya(iraisya);
			iraisyaList.add(iraisya);
		}

		jikenbo.setIraisyaList(iraisyaList);
		jikenboService.saveJikenbo(jikenbo);

		return new ModelAndView("redirect:/jikenbos/" + jikenbo.getId());
	}

	/**
	 * 事件簿　詳細
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/jikenbos/{id}")
	public ModelAndView show(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "jikenbo/show::jikenbo_contents");
		mav.addObject("title", "事件簿詳細");
		mav.addObject("jikenbo", jikenboService.find(id));
		return mav;
	}

	/**
	 * 事件簿編集
	 * @param id
	 * @param mav
	 * @return
	 */
	@GetMapping("/jikenbos/{id}/edit")
	public ModelAndView edit(
			@PathVariable Integer id,
			ModelAndView mav
			) {
		mav.setViewName("layout");
		mav.addObject("contents", "jikenbo/edit::jikenbo_contents");
		mav.addObject("title", "事件簿編集");
		mav.addObject("jikenbo", jikenboService.find(id));
		mav.addObject("customer", customerService.allget());
		return mav;
	}

	@PostMapping("/jikenbos/{id}/edit")
	public ModelAndView update(
			@PathVariable Integer id,
			@ModelAttribute Jikenbo jikenbo,
			@RequestParam("customer") Integer[] customers,
			@RequestParam(name = "addr", defaultValue=" ") String[] addrs
			){

		Jikenbo ejikenbo = jikenboService.find(id);
		ejikenbo.setJikenName(jikenbo.getJikenName());
		if(jikenbo.getJikenNumber() != null || !(jikenbo.getJikenNumber().isEmpty())) {
			ejikenbo.setJikenNumber(jikenbo.getJikenNumber());
		}
		ejikenbo.setJyuninDate(jikenbo.getJyuninDate());
		ejikenbo.setKensu(jikenbo.getKensu());
		ejikenbo.setSyubetsu(jikenbo.getSyubetsu());
		List<Iraisya> iraisyaList = iraisyaService.iraisyaset(ejikenbo.getIraisyaList(), customers, addrs);
		ejikenbo.setIraisyaList(iraisyaList);
		jikenboService.saveJikenbo(ejikenbo);
		return new ModelAndView("redirect:/jikenbos/{id}");
	}


	/**
	 * 事件簿削除
	 * @param id
	 * @return
	 */
	 @PostMapping("/jikenbos/{id}/delete")
	 public ModelAndView delete(
			 @PathVariable Integer id
			 ) {
		 jikenboService.delete(id);
		 return new ModelAndView("redirect:/jikenbos");
	 }

	 /**
	  * 依頼者追加
	  * @param id
	  * @param mav
	  * @return
	  */
	 @GetMapping("/jikenbos/{id}/iraisyanew")
	 public ModelAndView iraisyaNew(
			 @PathVariable Integer id,
			 ModelAndView mav
			 ) {
		 mav.setViewName("layout");
		 mav.addObject("contents", "jikenbo/iraisyanew::jikenbo_contents");
		 mav.addObject("title", "依頼者追加");
		 mav.addObject("jikenbo", jikenboService.find(id));
		 mav.addObject("customer", customerService.allget());
		 return mav;
	 }

	 @PostMapping("/jikenbos/{id}/iraisyanew")
	 public ModelAndView iraisyacreate(
			@PathVariable Integer id,
			@RequestParam("customer") Customer customer,
			@RequestParam(name="addr", required=false) String addr
			) {
		 Jikenbo jikenbo = jikenboService.find(id);
		 List<Iraisya> iraisyaList = jikenbo.getIraisyaList();
		 Iraisya iraisya = new Iraisya();
		 iraisya.setCustomer(customer);
		 iraisya.setAddr(addr);
		 iraisyaService.saveIraisya(iraisya);
		 iraisyaList.add(iraisya);
		 jikenbo.setIraisyaList(iraisyaList);
		 jikenboService.saveJikenbo(jikenbo);
		 return new ModelAndView("redirect:/jikenbos/{id}");
	 }

	 /**
	  * 依頼者削除
	  * @param id
	  * @param iid
	  * @return
	  */
	 @PostMapping("/jikenbos/{id}/iraisyadelete/{iid}")
	 public ModelAndView iraisyaDelete(
			 @PathVariable Integer id,
			 @PathVariable Integer iid
			 ) {
		 Jikenbo jikenbo = jikenboService.find(id);
		 List<Iraisya> iraisyaList = jikenbo.getIraisyaList();
		 Iraisya iraisya = iraisyaService.find(iid);
		 iraisyaList.remove(iraisya);
		 jikenbo.setIraisyaList(iraisyaList);
		 jikenboService.saveJikenbo(jikenbo);
		 iraisyaService.delete(iid);

		 return new ModelAndView("redirect:/jikenbos/{id}");

	 }
}
