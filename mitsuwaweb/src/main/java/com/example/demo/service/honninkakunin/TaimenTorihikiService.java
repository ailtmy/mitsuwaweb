package com.example.demo.service.honninkakunin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.honninkakunin.TaimenTorihiki;
import com.example.demo.repository.honninkakunin.TaimenTorihikiRepository;

@Service
public class TaimenTorihikiService {

	@Autowired
	TaimenTorihikiRepository repository;

	public TaimenTorihiki saveTaimenTorihiki(TaimenTorihiki taimenTorihiki) {
		return repository.saveAndFlush(taimenTorihiki);
	}

	public TaimenTorihiki findByHonninKakuninId(Integer hid){
		return repository.findByHonninKakuninId(hid).orElse(null);
	}

}
