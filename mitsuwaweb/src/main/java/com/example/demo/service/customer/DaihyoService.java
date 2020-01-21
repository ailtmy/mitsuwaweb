package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Daihyo;
import com.example.demo.repository.customer.DaihyoRepository;

@Service
public class DaihyoService {

	@Autowired
	DaihyoRepository repository;

	public Daihyo saveDaihyo(Daihyo dai) {
		return repository.saveAndFlush(dai);

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public Daihyo find(Integer id) {
		return repository.findById(id).orElse(new Daihyo());
	}
}
