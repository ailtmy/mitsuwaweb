package com.example.demo.soft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.customer.Customer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Gimusya {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String addr;

	private String daihyo;

	private String shikibetsuUmu;

	private String shikibetsuRiyu;
}
