package com.example.demo.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.customer.CustomerTel;

public interface CustomerTelRepository extends JpaRepository<CustomerTel, Integer> {

}
