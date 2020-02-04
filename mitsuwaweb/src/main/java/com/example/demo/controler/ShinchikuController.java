package com.example.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.service.project.ShinchikuService;

@Controller
public class ShinchikuController {

	@Autowired
	ShinchikuService shinchikuService;


}
