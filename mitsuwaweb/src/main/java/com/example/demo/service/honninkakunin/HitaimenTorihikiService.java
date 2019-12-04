package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.honninkakunin.HitaimenTorihiki;
import com.example.demo.repository.honninkakunin.HitaimenTorihikiRepository;

@Service
public class HitaimenTorihikiService {

	@Autowired
	HitaimenTorihikiRepository repository;

	public HitaimenTorihiki saveHitaimenTorihiki(HitaimenTorihiki hitaimenTorihiki) {
		return repository.saveAndFlush(hitaimenTorihiki);
	}

	public HitaimenTorihiki findByHonninKakuninId(Integer hid){
		return repository.findByHonninKakuninId(hid).orElse(null);
	}

}
