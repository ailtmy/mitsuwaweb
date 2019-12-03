package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.honninkakunin.CustomerAddress;
import com.example.demo.repository.honninkakunin.CustomerAddressRepository;

@Service
public class CustomerAddressService {

	@Autowired
	CustomerAddressRepository repository;

	public CustomerAddress save(CustomerAddress customerAddress) {
		return repository.saveAndFlush(customerAddress);
	}
}
