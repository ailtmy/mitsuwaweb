package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.repository.GimusyaRepository;

@Service
public class GimusyaService {

	@Autowired
	GimusyaRepository repository;

	public Gimusya saveGimusya(Gimusya gimusya) {
		return repository.saveAndFlush(gimusya);
	}

	public Gimusya find(Integer id) {
		return repository.findById(id).orElse(new Gimusya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
