package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.repository.KenrisyaRepository;

@Service
public class KenrisyaService {

	@Autowired
	KenrisyaRepository repository;

	public Kenrisya saveKenrisya(Kenrisya kenrisya) {
		return repository.saveAndFlush(kenrisya);
	}

	public Kenrisya find(Integer id) {
		return repository.findById(id).orElse(new Kenrisya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);

	}
}
