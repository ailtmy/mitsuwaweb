package com.example.demo.repository.honninkakunin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.honninkakunin.HonninKakunin;

@Repository
public interface HonninKakuninRepository extends JpaRepository<HonninKakunin, Integer> {

	public Page<HonninKakunin> findByCustomerOrderByIdDesc(Customer customer, Pageable pageable);

}
