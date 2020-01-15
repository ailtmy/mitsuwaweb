package com.example.demo.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MailAudit;
import com.example.demo.entity.customer.CustomerMail;

@Repository
public interface CustomerMailRepository extends JpaRepository<CustomerMail, Integer> {

	CustomerMail save(MailAudit mailAddress);

}
