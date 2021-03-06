package com.example.demo.repository.customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Page<Customer> findAllByOrderById(Pageable pageable);

	Page<Customer> findDistinctByNameContainingOrKanaContainingOrMailList_MailAddrContainingOrTelephoneList_PhoneNumberContainingOrderByKana(
			String name, String kana, String mailAddr, String phoneNumber, Pageable pageable);

	public List<Customer> findAllByOrderByKana();

}
