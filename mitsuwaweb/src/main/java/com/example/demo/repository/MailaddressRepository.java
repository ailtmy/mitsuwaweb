package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Mailaddress;

@Repository
public interface MailaddressRepository extends JpaRepository<Mailaddress, Integer> {

}
