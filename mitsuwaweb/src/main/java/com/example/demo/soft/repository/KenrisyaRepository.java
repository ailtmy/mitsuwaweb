package com.example.demo.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.Kenrisya;

@Repository
public interface KenrisyaRepository extends JpaRepository<Kenrisya, Integer> {

}
