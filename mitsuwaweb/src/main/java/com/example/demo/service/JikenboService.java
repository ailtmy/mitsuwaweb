package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.jikenbo.Jikenbo;
import com.example.demo.repository.JikenboRepository;

@Service
public class JikenboService {

	@Autowired
	JikenboRepository repository;

	public Page<Jikenbo> getAll(Pageable pageable) {
		return repository.findAllByOrderByJyuninDate(pageable);
	}

	public Jikenbo saveJikenbo(Jikenbo jikenbo) {
		return repository.saveAndFlush(jikenbo);
	}

	public Jikenbo find(Integer id) {
		return repository.findById(id).orElse(new Jikenbo());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}


}
