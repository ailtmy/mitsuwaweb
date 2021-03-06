package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Tochi;
import com.example.demo.soft.repository.TochiRepository;

@Service
public class TochiService {

	@Autowired
	TochiRepository repository;

	public Page<Tochi> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Tochi saveTochi(Tochi tochi) {
		return repository.saveAndFlush(tochi);
	}

	public Tochi find(Integer id) {
		return repository.findById(id).orElse(new Tochi());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
