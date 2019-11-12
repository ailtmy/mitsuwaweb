package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomerFile;

@Repository
public interface CustomerFileRepository extends JpaRepository<CustomerFile, Integer> {

}
