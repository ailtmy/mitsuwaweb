package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.SenyuTatemono;
import com.example.demo.soft.repository.SenyuTatemonoRepository;

@Service
public class SenyuTatemonoService {

	@Autowired
	SenyuTatemonoRepository repository;

	public Page<SenyuTatemono> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public SenyuTatemono saveSenyu(SenyuTatemono senyu) {
		return repository.saveAndFlush(senyu);
	}

	public SenyuTatemono find(Integer id) {
		return repository.findById(id).orElse(new SenyuTatemono());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
