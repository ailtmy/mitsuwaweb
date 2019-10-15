package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Integer> {

}
