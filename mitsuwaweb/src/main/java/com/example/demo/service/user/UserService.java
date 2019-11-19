package com.example.demo.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.user.User;
import com.example.demo.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Page<User> getAll(Pageable pageable){
		return  repository.findAllByOrderById(pageable);
	}

	public User saveUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		return repository.saveAndFlush(user);
	}

	public User saveUserImage(User user) {
		return repository.saveAndFlush(user);
	}

	public User find(Integer id) {
		return repository.findById(id).orElse(new User());

	}

	public User findByName(String name) {
		return repository.findByName(name);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public Page<User> search(String name, String mailAddr, String phoneNumber, Pageable pageable){
		return repository.findDistinctByNameContainingOrMailList_MailAddrContainingOrTelephoneList_PhoneNumberContainingOrderById(
				name, mailAddr, phoneNumber, pageable);
	}


}
