package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.honninkakunin.HonninKakunin;
import com.example.demo.repository.honninkakunin.HonninKakuninRepository;

@Service
public class HonninKakuninService {

	@Autowired
	HonninKakuninRepository repository;

	public Page<HonninKakunin> getByCustomer(Customer customer, Pageable pageable) {
		return repository.findByCustomerOrderByIdDesc(customer, pageable);
	}

	public HonninKakunin saveHonninKakunin(HonninKakunin honninKakunin) {
		return repository.saveAndFlush(honninKakunin);
	}

	public HonninKakunin find(Integer id) {
		return repository.findById(id).orElse(null);
	}

}
