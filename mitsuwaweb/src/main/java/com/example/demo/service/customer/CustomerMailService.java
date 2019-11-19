package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.CustomerMail;
import com.example.demo.repository.customer.CustomerMailRepository;

@Service
public class CustomerMailService {

	@Autowired
	CustomerMailRepository repository;

	public CustomerMail saveCustomerMail(CustomerMail mailaddress) {
		return repository.save(mailaddress);
	}

	public CustomerMail find(Integer id) {
		return repository.findById(id).orElse(new CustomerMail());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
