package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.honninkakunin.HonninKakuninFile;
import com.example.demo.repository.honninkakunin.HonninKakuninFileRepository;

@Service
public class HonninKakuninFileService {

	@Autowired
	HonninKakuninFileRepository repository;

	public HonninKakuninFile saveHonninKakuninFile(HonninKakuninFile honninKakuninFile) {
		return repository.saveAndFlush(honninKakuninFile);
	}

}
