package com.example.demo.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView getLogin(ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "login/login::login_contents");
		return mav;
	}

	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}

	@GetMapping("/admin")
	public ModelAndView getAdmin(ModelAndView mav) {
		mav.setViewName("layout");
		mav.addObject("contents", "login/admin::admin_contents");
		return mav;
	}
}
