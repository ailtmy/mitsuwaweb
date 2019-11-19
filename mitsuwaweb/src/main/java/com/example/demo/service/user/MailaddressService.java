package com.example.demo.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.user.Mailaddress;
import com.example.demo.repository.user.MailaddressRepository;

@Service
public class MailaddressService {

	@Autowired
	MailaddressRepository repository;

	public Mailaddress saveMailaddress(Mailaddress mailaddress) {
		return repository.save(mailaddress);
	}

	public Mailaddress find(Integer id) {
		return repository.findById(id).orElse(new Mailaddress());

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
