package com.example.demo.entity.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.demo.entity.MailAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class CustomerMail extends MailAudit {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToOne
//	@JoinColumn(name = "customerId")
//	private Customer customer;
}
