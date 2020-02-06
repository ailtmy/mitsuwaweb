package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.jikenbo.Jikenbo;

@Repository
public interface JikenboRepository extends JpaRepository<Jikenbo, Integer> {

	Page<Jikenbo> findAllByOrderByJyuninDate(Pageable pageable);

}
