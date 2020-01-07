package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.repository.ShinseiBukkenRepository;

@Service
public class ShinseiBukkenService {

	@Autowired
	ShinseiBukkenRepository repository;

	public Page<ShinseiBukken> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public List<ShinseiBukken> allget(){
		return repository.findAllByOrderByFudosanBango();
	}

	public ShinseiBukken saveTochi(ShinseiBukken shinseiBukken) {
		return repository.saveAndFlush(shinseiBukken);
	}

	public ShinseiBukken find(Integer id) {
		return repository.findById(id).orElse(new ShinseiBukken());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
