package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.jikenbo.Iraisya;
import com.example.demo.repository.IraisyaRepository;
import com.example.demo.service.customer.CustomerService;

@Service
public class IraisyaService {

	@Autowired
	IraisyaRepository repository;

	@Autowired
	CustomerService customerService;

	public Iraisya saveIraisya(Iraisya iraisya) {
		return repository.saveAndFlush(iraisya);
	}

	public List<Iraisya> iraisyaset(List<Iraisya> iraisyaList, Integer[] customers, String[] addrs){
		if(!iraisyaList.isEmpty()) {
			for(int i = 0; i < customers.length; i++) {
				Iraisya iraisya = iraisyaList.get(i);
				Customer customer = customerService.find(customers[i]);
				iraisya.setCustomer(customer);
				if(addrs[i] != null || !(addrs[i].isEmpty())) {
					iraisya.setAddr(addrs[i]);
				}
				saveIraisya(iraisya);
//				iraisyaList.add(iraisya);
			}
		}
		return iraisyaList;
	}

	public Iraisya find(Integer id) {
		return repository.findById(id).orElse(new Iraisya());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
}
