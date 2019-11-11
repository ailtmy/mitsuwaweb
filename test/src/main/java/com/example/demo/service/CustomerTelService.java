package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerTel;
import com.example.demo.repository.CustomerTelRepository;

@Service
public class CustomerTelService {

	@Autowired
	CustomerTelRepository repository;

	public CustomerTel saveCustomerTel(CustomerTel telephone) {
		return repository.save(telephone);
	}

	public CustomerTel find(Integer id) {
		return repository.findById(id).orElse(new CustomerTel());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
