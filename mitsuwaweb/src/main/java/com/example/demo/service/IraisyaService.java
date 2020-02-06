package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.jikenbo.Iraisya;
import com.example.demo.repository.IraisyaRepository;

@Service
public class IraisyaService {

	@Autowired
	IraisyaRepository repository;

	public Iraisya saveIraisya(Iraisya iraisya) {
		return repository.saveAndFlush(iraisya);
	}
}
