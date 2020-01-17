package com.example.demo.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.customer.Person;
import com.example.demo.repository.customer.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public Page<Person> getAll(Pageable pageable) {
		return repository.findAllByOrderById(pageable);
	}

	public Person savePerson(Person person) {
		return repository.saveAndFlush(person);
	}

	public Person find(Integer id) {
		return repository.findById(id).orElse(new Person());
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
