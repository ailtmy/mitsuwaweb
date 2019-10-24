package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Page<User> findAllByOrderById(Pageable pageable);

	Page<User> findDistinctByNameContainingOrMailContainingOrTelephoneList_PhoneNumberContainingOrderById(
			String name, String mail, String phoneNumber, Pageable pageable);

}
