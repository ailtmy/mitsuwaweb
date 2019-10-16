package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Telephone;
import com.example.demo.entity.User;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	TelephoneService telephoneService;


//	<<------- ユーザー一覧 ------------->>
	@GetMapping("/users")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー一覧");
		List<User> list = userService.getAll();
		mav.addObject("list", list);
		return mav;
	}

//  <<------------ユーザー新規作成画面表示----------------->>
	@GetMapping("/users/new")
	public ModelAndView usernew(ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/new::user_contents");
		mav.addObject("title", "ユーザー新規作成");
		return mav;
	}

//  <<------------ユーザー新規作成画面表示----------------->>
	@PostMapping("users/new")
	public ModelAndView save(
			@RequestParam("phoneKind") String phoneKind,
			@RequestParam("phoneNumber") String phoneNumber,
			@ModelAttribute("formModel")
			@Validated User user,
			BindingResult result,
			ModelAndView mav) {
	ModelAndView res = null;
	if(!result.hasErrors()) {
		List<Telephone> list = new ArrayList<Telephone>();
		Telephone telephone = new Telephone();
		telephone.setPhoneKind(phoneKind);
		telephone.setPhoneNumber(phoneNumber);
		list.add(telephone);
		user.setTelephoneList(list);
		userService.saveUser(user);
		res = new ModelAndView("redirect:/users");
	} else {
		mav.setViewName("layout");
		mav.addObject("msg", "sorry, error is occured...");
		mav.addObject("contents", "user/new::user_contents");
		mav.addObject("title", "ユーザー新規作成");
		res = mav;
	}
		return res;
	}

// <<------------ユーザー詳細---------------->>
	@GetMapping("/users/{id}")
	public ModelAndView show(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/show::user_contents");
		mav.addObject("user", userService.find(id));
		mav.addObject("title", "ユーザー詳細");
		return mav;
	}
}
