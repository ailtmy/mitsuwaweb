package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MailAudit;

@Repository
public interface MailAuditRepository extends JpaRepository<MailAudit, Integer> {

}
