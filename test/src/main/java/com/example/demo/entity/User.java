package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Setter
@Getter
public class User {

	@Id
	@Column
	private int id;

	@Column
	private String name;

	@Column
	@Email
	private String mail;
}
