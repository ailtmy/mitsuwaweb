package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Company;
import com.example.demo.entity.customer.Customer;
import com.example.demo.service.customer.CustomerService;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.repository.KenrisyaRepository;

@Service
public class KenrisyaService {

	@Autowired
	KenrisyaRepository repository;

	@Autowired
	CustomerService customerService;

	public Kenrisya saveKenrisya(Kenrisya kenrisya) {
		return repository.saveAndFlush(kenrisya);
	}

	public Kenrisya find(Integer id) {
		return repository.findById(id).orElse(new Kenrisya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);

	}

	public List<Kenrisya> newMassyoKenrisya(List<Kenrisya> kenrisyaList, Integer[] customers,
			String[] addrs,  String[] daihyos){

		for(int i = 0; i < customers.length; i++) {
			Kenrisya kenrisya = new Kenrisya();
			Customer customer = customerService.find(customers[i]);
			kenrisya.setCustomer(customer);
			if(addrs[i] != null || !(addrs[i].isEmpty())) {
				kenrisya.setAddr(addrs[i]);
			}
			if(customer instanceof Company && daihyos.length != 0) {
				if(daihyos[i] != null || !(daihyos[i].isEmpty())) {
					kenrisya.setDaihyo(daihyos[i]);
				}
			}
			saveKenrisya(kenrisya);
			kenrisyaList.add(kenrisya);
		}

		return kenrisyaList;
	}
}
