package com.example.demo.repository.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.project.Shinchiku;

@Repository
public interface ShinchikuRepository extends JpaRepository<Shinchiku, Integer> {

	public Page<Customer> findAllByOrderByIdDesc(Pageable pageable);
}
