package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CustomerTel;

public interface CustomerTelRepository extends JpaRepository<CustomerTel, Integer> {

}
