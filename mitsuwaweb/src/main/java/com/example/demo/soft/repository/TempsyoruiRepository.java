package com.example.demo.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.Tempsyorui;

@Repository
public interface TempsyoruiRepository extends JpaRepository<Tempsyorui, Integer> {

}
