package com.example.demo.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.project.ShinchikuRepository;

@Service
public class ShinchikuService {

	@Autowired
	ShinchikuRepository repository;
}
