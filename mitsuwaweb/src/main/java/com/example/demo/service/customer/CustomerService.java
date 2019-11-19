package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Customer;
import com.example.demo.repository.customer.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository repository;

	public Page<Customer> getAll(Pageable pageable){
		return repository.findAllByOrderById(pageable);
	}

	public Customer saveCustomer(Customer customer) {
		return repository.saveAndFlush(customer);
	}

	public Customer find(Integer id) {
		return repository.findById(id).orElse(new Customer());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public Page<Customer> search(String name, String kana, String mailAddr, String phoneNumber, Pageable pageable){
		return repository.findDistinctByNameContainingOrKanaContainingOrMailList_MailAddrContainingOrTelephoneList_PhoneNumberContainingOrderByKana(
				name, kana, mailAddr, phoneNumber, pageable);
	}


}
