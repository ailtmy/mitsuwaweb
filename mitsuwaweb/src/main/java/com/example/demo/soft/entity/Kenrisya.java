package com.example.demo.soft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.entity.customer.Customer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Kenrisya {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Customer customer;

	private String mochibun;


}
