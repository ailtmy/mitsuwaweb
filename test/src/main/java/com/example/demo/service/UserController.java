package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;

@Controller
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/users")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "user/index::user_contents");
		mav.addObject("title", "ユーザー一覧");
		List<User> list = service.getAll();
		mav.addObject("list", list);
		return mav;
	}
}
