package com.example.demo.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.customer.Daihyo;

@Repository
public interface DaihyoRepository extends JpaRepository<Daihyo, Integer> {


}
