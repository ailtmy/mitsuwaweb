package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.FuzokuTatemono;
import com.example.demo.soft.repository.FuzokuTatemonoRepository;

@Service
public class FuzokuTatemonoService {

	@Autowired
	FuzokuTatemonoRepository repository;

	public FuzokuTatemono saveFuzoku(FuzokuTatemono fuzoku) {
		return repository.saveAndFlush(fuzoku);
	}
}
