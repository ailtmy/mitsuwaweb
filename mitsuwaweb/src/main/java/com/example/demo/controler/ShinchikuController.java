package com.example.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.service.project.ShinchikuMansyonService;

@Controller
public class ShinchikuController {

	@Autowired
	ShinchikuMansyonService shinchikuService;


}
