package com.example.demo.soft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.entity.HozonRepository;

@Service
public class HozonService {

	@Autowired
	HozonRepository repository;

	public Page<Hozon> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Hozon find(Integer id) {
		return repository.findById(id).orElse(new Hozon());
	}

	public Hozon saveHozon(Hozon hozon) {
		return repository.saveAndFlush(hozon);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}


}
