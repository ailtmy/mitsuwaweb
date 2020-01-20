package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Company;
import com.example.demo.repository.customer.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository repository;

	public Page<Company> getAll(Pageable pageable) {
		return repository.findAllByOrderById(pageable);
	}

	public Company saveCompany(Company company) {
		return repository.saveAndFlush(company);
	}

	public Company find(Integer id) {
		return repository.findById(id).orElse(new Company());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
