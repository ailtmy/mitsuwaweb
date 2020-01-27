package com.example.demo.soft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.Teitouken;

@Repository
public interface TeitoukenRepository extends JpaRepository<Teitouken, Integer> {

	public Page<Teitouken> findAllByOrderByIdDesc(Pageable pageable);
}
