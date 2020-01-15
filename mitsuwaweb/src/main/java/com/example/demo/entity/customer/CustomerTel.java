package com.example.demo.entity.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.demo.entity.TelAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class CustomerTel extends TelAudit {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToMany(mappedBy = "telephoneList")
//	private List<Customer> customerList;

}
