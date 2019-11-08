package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Page<User> findAllByOrderById(Pageable pageable);

	public User findByName(String name);

	Page<User> findDistinctByNameContainingOrMailList_MailAddrContainingOrTelephoneList_PhoneNumberContainingOrderById(
			String name, String mailAddr, String phoneNumber, Pageable pageable);

}
