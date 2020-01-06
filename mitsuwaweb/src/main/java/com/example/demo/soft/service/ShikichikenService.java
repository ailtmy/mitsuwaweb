package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Shikichiken;
import com.example.demo.soft.repository.ShikichikenRepository;

@Service
public class ShikichikenService {

	@Autowired
	ShikichikenRepository repository;

	public Shikichiken saveShikichiken(Shikichiken shikichiken) {
		return repository.saveAndFlush(shikichiken);
	}

	public Shikichiken find(Shikichiken shikichiken) {
		return repository.findById(shikichiken.getId()).orElse(new Shikichiken());
	}

	public void delete(Shikichiken shikichiken) {
		repository.delete(shikichiken);
	}

	public Shikichiken findById(Integer id) {
		return repository.findById(id).orElse(new Shikichiken());
	}
}
