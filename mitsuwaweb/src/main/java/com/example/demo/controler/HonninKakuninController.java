package com.example.demo.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.honninkakunin.CustomerAddress;
import com.example.demo.entity.honninkakunin.HonninKakunin;
import com.example.demo.entity.honninkakunin.HonninKakuninFile;
import com.example.demo.entity.honninkakunin.KakuninSyorui;
import com.example.demo.entity.honninkakunin.TaimenTorihiki;
import com.example.demo.entity.user.User;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.service.honninkakunin.CustomerAddressService;
import com.example.demo.service.honninkakunin.HonninKakuninService;
import com.example.demo.service.honninkakunin.KakuninSyoruiService;
import com.example.demo.service.honninkakunin.TaimenTorihikiService;
import com.example.demo.service.user.UserService;

@Controller
public class HonninKakuninController {

	@Autowired
	HonninKakuninService honninKakuninService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerAddressService customerAddressService;

	@Autowired
	TaimenTorihikiService taimenTorihikiService;

	@Autowired
	KakuninSyoruiService kakuninSyoruiService;

	@Autowired
	UserService userService;

	/**
	 * 顧客別本人確認記録
	 */
	@GetMapping("/customers/{id}/idents")
	public ModelAndView identIndex(
			@PathVariable Integer id,
			@PageableDefault(page=0, size=5)
			Pageable pageable,
			ModelAndView mav) {
		Customer customer = customerService.find(id);
		Page<HonninKakunin> list = honninKakuninService.getByCustomer(customer, pageable);
		mav.setViewName("layout");
		mav.addObject("contents", "honninkakunin/index::honnninkakunin_contents");
		mav.addObject("title", "本人確認記録");
		mav.addObject("list", list);
		mav.addObject("customer",customer);
		return mav;
	}

	/**
	 * 対面取引本人確認記録新規作成画面
	 */
	@GetMapping("/customers/{id}/idents/tnew")
	public ModelAndView tnew(
			@PathVariable Integer id,
			@ModelAttribute HonninKakunin honninkakunin,
			ModelAndView mav) {
		Customer customer = customerService.find(id);
		List<User> users = userService.findAll();
		mav.setViewName("layout");
		mav.addObject("contents", "honninkakunin/taimennew::honninkakunin_contents");
		mav.addObject("title", "対面取引本人確認記録作成");
		mav.addObject("customer", customer);
		mav.addObject("users", users);
		return mav;
	}

	/**
	 * 対面取引本人確認記録作成
	 * @throws IOException
	 */
	@PostMapping("/customers/{customerId}/idents/tnew")
	public ModelAndView tcreat(
			@PathVariable Integer customerId,
			@RequestParam("file") MultipartFile[] files,
			@ModelAttribute("CustomerAddress") CustomerAddress customerAddress,
			@ModelAttribute("TaimenTorihiki") TaimenTorihiki taimenTorihiki,
			@ModelAttribute("KakuninSyorui") KakuninSyorui kakuninSyorui,
			@ModelAttribute("HonninKakunin") HonninKakunin honninKakunin,
			BindingResult result,
			ModelAndView mav) throws IOException {

		if(!result.hasErrors()) {
			Customer customer = customerService.find(customerId);
			honninKakunin.setCustomer(customer);

			List<CustomerAddress> addresses = new ArrayList<CustomerAddress>();

			honninKakuninService.saveHonninKakunin(honninKakunin);
			customerAddress.setHonninKakunin(honninKakunin);
			CustomerAddress address = customerAddressService.save(customerAddress);
			addresses.add(address);
			honninKakunin.setAddressList(addresses);

			taimenTorihiki.setHonninKakunin(honninKakunin);
			taimenTorihikiService.saveTaimenTorihiki(taimenTorihiki);
			honninKakunin.setTaimen(taimenTorihiki);

			kakuninSyorui.setHonninKakunin(honninKakunin);
			List<HonninKakuninFile> kakuninSyoruiFileList = new ArrayList<HonninKakuninFile>();
			for(MultipartFile file : files) {
				HonninKakuninFile honninKakuninFile = new HonninKakuninFile();
				honninKakuninFile.setFileName(file.getOriginalFilename());
				honninKakuninFile.setFile(file.getBytes());
				honninKakuninFile.setKakuninSyorui(kakuninSyorui);
				kakuninSyoruiFileList.add(honninKakuninFile);
			}
			kakuninSyorui.setKakuninSyoruiFileList(kakuninSyoruiFileList);
			kakuninSyoruiService.saveKakuninSyorui(kakuninSyorui);

			List<KakuninSyorui> kakuninSyoruiList = new ArrayList<KakuninSyorui>();
			kakuninSyoruiList.add(kakuninSyorui);
			honninKakunin.setKakuninSyoruiList(kakuninSyoruiList);



			mav.setViewName("layout");
			mav.addObject("contents", "honninkakunin/taimennew::honninkakunin_contents");
			mav.addObject("title", "対面取引本人確認記録作成");
			mav.addObject("customer", customer);
		}
			return new ModelAndView("redirect:/customers/" + customerId + "/idents");

	}


}
