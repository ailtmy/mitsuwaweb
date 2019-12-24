package com.example.demo.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.FuzokuTatemono;

@Repository
public interface FuzokuTatemonoRepository extends JpaRepository<FuzokuTatemono, Integer> {

}
