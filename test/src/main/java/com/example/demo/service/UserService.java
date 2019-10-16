package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> getAll(){
		return repository.findAll();
	}

	public User saveUser(User user) {
		return repository.save(user);
	}

	public User find(Integer id) {
		return repository.findById(id).orElse(new User());

	}
}
