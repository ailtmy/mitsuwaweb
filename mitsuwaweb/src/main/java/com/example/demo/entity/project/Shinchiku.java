package com.example.demo.entity.project;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.demo.entity.customer.Customer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Shinchiku extends Project {

	@ManyToMany
	private List<Customer> jigyonushi;

	@ManyToMany
	private List<Customer> hanbaidairi;

	@ManyToMany
	private List<Customer> kainushi;

}
