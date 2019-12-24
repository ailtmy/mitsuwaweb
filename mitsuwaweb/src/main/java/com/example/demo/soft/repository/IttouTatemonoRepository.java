package com.example.demo.soft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.IttouTatemono;

@Repository
public interface IttouTatemonoRepository extends JpaRepository<IttouTatemono, Integer> {

	Page<IttouTatemono> findAllByOrderByIdDesc(Pageable pageable);
}
