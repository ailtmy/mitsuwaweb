package com.example.demo.soft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.Hozon;

@Repository
public interface HozonRepository extends JpaRepository<Hozon, Integer> {

	public Page<Hozon> findAllByOrderByIdDesc(Pageable pageable);
}
