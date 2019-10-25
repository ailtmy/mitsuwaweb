package com.example.demo.controler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.service.TelephoneService;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	TelephoneService telephoneService;

//	<<------- ユーザー一覧 ------------->>
	@GetMapping("/users")
	public ModelAndView index(ModelAndView mav,
			@PageableDefault(page=0, size=5)
			Pageable pageable) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー一覧");
		Page<User> list = userService.getAll(pageable);
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

//  <<------------ユーザー新規作成----------------->>
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
			ModelAndView mav) throws IOException {
		User user = userService.find(id);
		byte[] bytes = user.getFile();
		try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("./usercard.jpeg"))){
			out.write(bytes);
			mav.addObject("file", out);
		}
		mav.setViewName("layout");
		mav.addObject("contents", "user/show::user_contents");
		mav.addObject("user", user);
		mav.addObject("title", "ユーザー詳細");
		return mav;
	}

// <<-----------ユーザー編集画面表示------------->>
	@GetMapping("/users/{id}/edit")
	public ModelAndView edit(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/edit::user_contents");
		mav.addObject("title", "ユーザー編集");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<----------ユーザー編集--------------------->>
	@PostMapping("/users/{id}/edit")
	public ModelAndView edited(@PathVariable Integer id,
			@RequestParam("name") String name,
			@RequestParam("mail") String mail,
			@RequestParam(name = "phoneKind", required = false) String[] phoneKinds,
			@RequestParam(name = "phoneNumber", required = false) String[] phoneNumbers,
			ModelAndView mav) {
		User user = userService.find(id);
		user.setId(id);
		user.setName(name);
		user.setMail(mail);
		if(!user.getTelephoneList().isEmpty()) {
			List<Telephone> tels = user.getTelephoneList();
			for(int i = 0; i < tels.size(); i++) {
				tels.get(i).setPhoneKind(phoneKinds[i]);
				tels.get(i).setPhoneNumber(phoneNumbers[i]);
			}
		}
		userService.saveUser(user);

		return new ModelAndView("redirect:./");
	}


//	<<--------------ユーザー削除------------------>>
	@PostMapping("/users/{id}/delete")
	public ModelAndView delete(@PathVariable Integer id,
			ModelAndView mav) {
		userService.delete(id);
		return new ModelAndView("redirect:/users");
	}


//	<<------- ユーザー検索 ------------->>
	@GetMapping("/users/search")
	public ModelAndView fsearch(
			@RequestParam("name") String name,
			@PageableDefault(page=0, size=5) Pageable pageable) {
		Page<User> list = userService.search(name, name, name,  pageable);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー検索");
		mav.addObject("list", list);
		return mav;
	}

// <<-----------ユーザー電話新規画面------------->>
	@GetMapping("users/{id}/telnew")
	public ModelAndView usertelephonenew(@PathVariable Integer id,
			ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "telephone/usertelephonenew::usertelephone_contents");
		mav.addObject("title", "ユーザー連絡先新規登録");
		mav.addObject("user", userService.find(id));
		return mav;
	}

// <<-----------ユーザー電話新規作成------------->>
	@PostMapping("/users/{id}/telnew")
	public ModelAndView usertelephonesave(@PathVariable Integer id,
		@RequestParam(name = "phoneKind", required = false) String phoneKind,
		@RequestParam(name = "phoneNumber", required = false) String phoneNumber,
		ModelAndView mav) {
			User user = userService.find(id);
			Telephone tel = new Telephone();
			List<Telephone> tels = user.getTelephoneList();
			tel.setPhoneKind(phoneKind);
			tel.setPhoneNumber(phoneNumber);
			tels.add(tel);
			user.setTelephoneList(tels);
			userService.saveUser(user);
			mav.setViewName("layout");
			mav.addObject("contents", "telephone/usertelephonenew::telephone_contents");
			mav.addObject("title", "ユーザー連絡先新規登録");
			mav.addObject("user", user);
			return new ModelAndView("redirect:/users/{id}");
	}

// <<------------ユーザー電話削除---------------->>
	@PostMapping("/users/{uid}/teldelete/{tid}")
	public ModelAndView usertelephonedeleted(
			@PathVariable Integer uid,
			@PathVariable int tid,
			ModelAndView mav) {
		User user = userService.find(uid);
		List<Telephone> tels = user.getTelephoneList();
		Telephone tel = telephoneService.find(tid);
		tels.remove(tel);
		user.setTelephoneList(tels);
		userService.saveUser(user);
		telephoneService.delete(tid);
		return new ModelAndView("redirect:/users/{uid}");
	}

}
