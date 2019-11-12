package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Telephone;
import com.example.demo.repository.TelephoneRepository;

@Service
public class TelephoneService {

	@Autowired
	TelephoneRepository repository;

	public Telephone saveTelephone(Telephone telephone) {
		return repository.save(telephone);
	}

	public Telephone find(Integer id) {
		return repository.findById(id).orElse(new Telephone());

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
