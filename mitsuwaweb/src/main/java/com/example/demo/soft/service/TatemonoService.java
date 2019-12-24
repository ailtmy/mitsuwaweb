package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Tatemono;
import com.example.demo.soft.repository.TatemonoRepository;

@Service
public class TatemonoService {

	@Autowired
	TatemonoRepository repository;

	public Page<Tatemono> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Tatemono saveTatemono(Tatemono tatemono) {
		return repository.saveAndFlush(tatemono);
	}

	public Tatemono find(Integer id) {
		return repository.findById(id).orElse(new Tatemono());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
