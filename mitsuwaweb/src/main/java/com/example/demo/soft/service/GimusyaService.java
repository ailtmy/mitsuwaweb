package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Customer;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.repository.GimusyaRepository;

@Service
public class GimusyaService {

	@Autowired
	GimusyaRepository repository;

	@Autowired
	CustomerService customerService;

	public Gimusya saveGimusya(Gimusya gimusya) {
		return repository.saveAndFlush(gimusya);
	}

	public Gimusya find(Integer id) {
		return repository.findById(id).orElse(new Gimusya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public List<Gimusya> setGimusyaList(List<Gimusya> gimusyaList, Integer[] gimusyas, String[] gimusyaaddrs, String[] gimusyadaihyos ) {
		if(!gimusyaList.isEmpty()) {
			for(int i = 0; i < gimusyas.length; i++) {
				Gimusya gimusya = gimusyaList.get(i);
				Customer customer = customerService.find(gimusyas[i]);
				gimusya.setCustomer(customer);
				gimusya.setAddr(gimusyaaddrs[i]);
				gimusya.setDaihyo(gimusyadaihyos[i]);
				saveGimusya(gimusya);
				gimusyaList.add(gimusya);
			}

		}
		return gimusyaList;
	}
}
