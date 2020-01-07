package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Tempsyorui;
import com.example.demo.soft.repository.TempsyoruiRepository;

@Service
public class TempsyoruiService {

	@Autowired
	TempsyoruiRepository repository;

	public Object getAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<Tempsyorui> allget(){
		return repository.findAll();
	}

	public Tempsyorui saveTempsyorui(Tempsyorui tempsyorui) {
		return repository.saveAndFlush(tempsyorui);
	}

	public Tempsyorui find(Integer id) {
		return repository.findById(id).orElse(new Tempsyorui());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}


}
