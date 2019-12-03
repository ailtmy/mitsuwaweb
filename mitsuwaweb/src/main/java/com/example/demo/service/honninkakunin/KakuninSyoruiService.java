package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.honninkakunin.KakuninSyorui;
import com.example.demo.repository.honninkakunin.KakuninSyoruiRepository;

@Service
public class KakuninSyoruiService {

	@Autowired
	KakuninSyoruiRepository repository;

	public KakuninSyorui saveKakuninSyorui(KakuninSyorui kakuninSyorui) {
		return repository.saveAndFlush(kakuninSyorui);
	}
}
