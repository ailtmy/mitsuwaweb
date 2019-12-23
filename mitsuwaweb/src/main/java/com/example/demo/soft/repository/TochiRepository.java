package com.example.demo.soft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.Tochi;

@Repository
public interface TochiRepository extends JpaRepository<Tochi, Integer> {

	Page<Tochi> findAllByOrderByIdDesc(Pageable pageable);

}
