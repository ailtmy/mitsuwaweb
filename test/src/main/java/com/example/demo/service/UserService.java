package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public Page<User> getAll(Pageable pageable){
		return  repository.findAllByOrderById(pageable);
	}

	public User saveUser(User user) {
		return repository.saveAndFlush(user);
	}

	public User find(Integer id) {
		return repository.findById(id).orElse(new User());

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public Page<User> search(String name, String mail, String phoneNumber, Pageable pageable){
		return repository.findDistinctByNameContainingOrMailContainingOrTelephoneList_PhoneNumberContainingOrderById(name, mail, phoneNumber, pageable);
	}


}
