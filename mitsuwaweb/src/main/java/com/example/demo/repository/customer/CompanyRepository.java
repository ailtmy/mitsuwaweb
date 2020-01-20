package com.example.demo.repository.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Page<Company> findAllByOrderById(Pageable pageable);
}
