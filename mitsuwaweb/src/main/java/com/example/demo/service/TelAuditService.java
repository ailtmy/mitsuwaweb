package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TelAudit;
import com.example.demo.repository.TelAuditRepository;

@Service
public class TelAuditService {

	@Autowired
	TelAuditRepository repository;

	public TelAudit saveTel(TelAudit tel) {
		return repository.saveAndFlush(tel);
	}

	public TelAudit find(Integer id) {
		return repository.findById(id).orElse(new TelAudit());

	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
