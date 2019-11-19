package com.example.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.user.Mailaddress;

@Repository
public interface MailaddressRepository extends JpaRepository<Mailaddress, Integer> {

}
