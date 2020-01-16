package com.example.demo.repository.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	Page<Person> findAllByOrderById(Pageable pageable);

}
