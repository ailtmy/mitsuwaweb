package com.example.demo.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.TaisyoBukken;

@Repository
public interface TaisyoBukkenRepository extends JpaRepository<TaisyoBukken, Integer> {

}
