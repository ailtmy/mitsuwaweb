package com.example.demo.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Company;
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

	public List<Gimusya> newMassyoGimusyaList(List<Gimusya> gimusyaList, Integer[] gimusyas,
			String[] gimusyaaddrs, String[] gimusyadaihyos, String[] shikibetsuumus, String[] shikibetsuriyus){

		for(int i = 0; i < gimusyas.length; i++) {
			Gimusya gimusya = new Gimusya();
			Customer customer = customerService.find(gimusyas[i]);
			gimusya.setCustomer(customer);
			if(gimusyaaddrs[i] != null || !(gimusyaaddrs[i].isEmpty())) {
				gimusya.setAddr(gimusyaaddrs[i]);
			}
			if(customer instanceof Company) {
				if(gimusyadaihyos[i] != null || !(gimusyadaihyos[i].isEmpty())) {
					gimusya.setDaihyo(gimusyadaihyos[i]);
				}
			}
			gimusya.setShikibetsuUmu(shikibetsuumus[i]);

			gimusya.setShikibetsuRiyu(shikibetsuriyus[i]);


			saveGimusya(gimusya);
			gimusyaList.add(gimusya);
		}

		return gimusyaList;
	}

	public List<Gimusya> editMassyoGimusyaList(List<Gimusya> gimusyaList, Integer[] gimusyas, String[] gimusyaaddrs,
			String[] gimusyadaihyos, String[] shikibetsuumus, String[] shikibetsuriyus){

		if(!gimusyaList.isEmpty()) {
			for(int i = 0; i < gimusyas.length; i++) {
				Gimusya gimusya = gimusyaList.get(i);
				Customer customer = customerService.find(gimusyas[i]);
				gimusya.setCustomer(customer);
				if(gimusyaaddrs[i] != null || (!gimusyaaddrs[i].isEmpty())) {
					gimusya.setAddr(gimusyaaddrs[i]);
				}
				if(gimusyadaihyos[i] != null || (!gimusyadaihyos[i].isEmpty())) {
					gimusya.setDaihyo(gimusyadaihyos[i]);
				}
				if(shikibetsuumus[i] != null || (!shikibetsuumus[i].isEmpty())) {
					gimusya.setShikibetsuUmu(shikibetsuumus[i]);
				}
				if(shikibetsuriyus[i] != null || (!shikibetsuriyus[i].isEmpty())) {
					gimusya.setShikibetsuRiyu(shikibetsuriyus[i]);
				}
				saveGimusya(gimusya);
				gimusyaList.add(gimusya);
			}
		}

		return gimusyaList;
	}

	public List<Gimusya> setGimusyaList(List<Gimusya> gimusyaList, Integer[] gimusyas,
			String[] gimusyaaddrs, String[] gimusyadaihyos, String[] shikibetsuumus, String[] shikibetsuriyus ) {
		if(!gimusyaList.isEmpty()) {
			for(int i = 0; i < gimusyas.length; i++) {
				Gimusya gimusya = gimusyaList.get(i);
				Customer customer = customerService.find(gimusyas[i]);
				gimusya.setCustomer(customer);
				if(gimusyaaddrs[i] != null || !(gimusyaaddrs[i].isEmpty())) {
					gimusya.setAddr(gimusyaaddrs[i]);
				}
				if(customer instanceof Company) {
					if(gimusyadaihyos[i] != null || !(gimusyadaihyos[i].isEmpty())) {
							gimusya.setDaihyo(gimusyadaihyos[i]);
					}
				}
				gimusya.setShikibetsuUmu(shikibetsuumus[i]);

				gimusya.setShikibetsuRiyu(shikibetsuriyus[i]);

				saveGimusya(gimusya);
				gimusyaList.add(gimusya);

			}

		}
		return gimusyaList;
	}

	public List<Gimusya> newMeihenShinseininList(List<Gimusya> gimusyaList, Integer[] gimusyas,
			String[] gimusyaaddrs, String[] gimusyadaihyos){

		for(int i = 0; i < gimusyas.length; i++) {
			Gimusya gimusya = new Gimusya();
			Customer customer = customerService.find(gimusyas[i]);
			gimusya.setCustomer(customer);
			if(gimusyaaddrs[i] != null || !(gimusyaaddrs[i].isEmpty())) {
				gimusya.setAddr(gimusyaaddrs[i]);
			}
			if(customer instanceof Company) {
				if(gimusyadaihyos[i] != null || !(gimusyadaihyos[i].isEmpty())) {
					gimusya.setDaihyo(gimusyadaihyos[i]);
				}
			}
			saveGimusya(gimusya);
			gimusyaList.add(gimusya);
		}

		return gimusyaList;
	}

	public List<Gimusya> editMeihenShinseininList(List<Gimusya> gimusyaList, Integer[] gimusyas,
			String[] gimusyaaddrs, String[] gimusyadaihyos){

		for(int i = 0; i < gimusyas.length; i++) {
			Gimusya gimusya = gimusyaList.get(i);
			Customer customer = customerService.find(gimusyas[i]);
			gimusya.setCustomer(customer);
			if(gimusyaaddrs[i] != null || !(gimusyaaddrs[i].isEmpty())) {
				gimusya.setAddr(gimusyaaddrs[i]);
			}
			if(customer instanceof Company) {
				if(gimusyadaihyos[i] != null || !(gimusyadaihyos[i].isEmpty())) {
					gimusya.setDaihyo(gimusyadaihyos[i]);
				}
			}
			saveGimusya(gimusya);
			gimusyaList.add(gimusya);
		}

		return gimusyaList;
	}
}
