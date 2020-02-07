package com.example.demo.entity.project;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.customer.Customer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class ShinchikuMansyon extends Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany
	private List<Customer> jigyonushi;

	@ManyToMany
	private List<Customer> hanbaidairi;

	@OneToMany
	private List<MansyonLot> lotList;

	@OneToOne
	private BukkenHyoji bukkenHyoji;

//	private Menkyozei menkyozei;

}
