package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Saimusya;
import com.example.demo.soft.repository.SaimusyaRepository;

@Service
public class SaimusyaService {

	@Autowired
	SaimusyaRepository repository;

	public Saimusya saveSaimusya(Saimusya saimusya) {
		return repository.saveAndFlush(saimusya);
	}

	public Saimusya find(Integer id) {
		return repository.findById(id).orElse(new Saimusya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
