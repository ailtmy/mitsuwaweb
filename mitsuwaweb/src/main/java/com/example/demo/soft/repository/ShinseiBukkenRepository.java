package com.example.demo.soft.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.soft.entity.ShinseiBukken;

@Repository
public interface ShinseiBukkenRepository extends JpaRepository<ShinseiBukken, Integer> {

	Page<ShinseiBukken> findAllByOrderByIdDesc(Pageable pageable);

	List<ShinseiBukken> findAllByOrderByFudosanBango();

}
