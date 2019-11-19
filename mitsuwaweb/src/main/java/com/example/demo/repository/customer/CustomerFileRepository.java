package com.example.demo.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.CustomerFile;

@Repository
public interface CustomerFileRepository extends JpaRepository<CustomerFile, Integer> {

}
