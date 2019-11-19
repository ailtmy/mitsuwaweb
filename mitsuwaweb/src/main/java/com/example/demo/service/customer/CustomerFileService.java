package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.CustomerFile;
import com.example.demo.repository.customer.CustomerFileRepository;

@Service
public class CustomerFileService {

	@Autowired
	CustomerFileRepository repository;

	public CustomerFile saveCustomerFile(CustomerFile file) {
		return repository.save(file);
	}

	public CustomerFile find(Integer id) {
		return repository.findById(id).orElse(new CustomerFile());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
