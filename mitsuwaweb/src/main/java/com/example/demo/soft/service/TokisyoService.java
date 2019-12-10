package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Tokisyo;
import com.example.demo.soft.repository.TokisyoRepository;

@Service
public class TokisyoService {

	@Autowired
	TokisyoRepository repository;

	public List<Tokisyo> findAll(){
		return repository.findAll();
	}

	public Tokisyo saveTokisho(Tokisyo tokisyo) {
		return repository.saveAndFlush(tokisyo);
	}

}
