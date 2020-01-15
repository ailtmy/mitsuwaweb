package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MailAudit;
import com.example.demo.repository.MailAuditRepository;

@Service
public class MailAuditService {

	@Autowired
	MailAuditRepository repository;

	public MailAudit saveMail(MailAudit mail) {
		return repository.saveAndFlush(mail);
	}

	public MailAudit find(Integer id) {
		return repository.findById(id).orElse(new MailAudit());

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
