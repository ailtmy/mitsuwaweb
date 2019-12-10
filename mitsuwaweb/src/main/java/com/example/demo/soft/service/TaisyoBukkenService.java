package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.TaisyoBukken;
import com.example.demo.soft.repository.TaisyoBukkenRepository;

@Service
public class TaisyoBukkenService {

	@Autowired
	TaisyoBukkenRepository repository;

	public TaisyoBukken saveBukken(TaisyoBukken bukken) {
		return repository.saveAndFlush(bukken);
	}
}
