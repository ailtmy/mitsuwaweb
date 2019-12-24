package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.IttouTatemono;
import com.example.demo.soft.repository.IttouTatemonoRepository;

@Service
public class IttouTatemonoService {

	@Autowired
	IttouTatemonoRepository repository;

	public Page<IttouTatemono> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public IttouTatemono saveIttou(IttouTatemono ittouTatemono) {
		return repository.saveAndFlush(ittouTatemono);
	}

	public IttouTatemono find(Integer id) {
		return repository.findById(id).orElse(new IttouTatemono());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
