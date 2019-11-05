package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="users")
@Setter
@Getter
public class User {

	public static enum Role {
		ROLE_ADMIN, ROLE_GENERAL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String name;

	@Email
	private String mail;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "usersTelephoneList",
			joinColumns = @JoinColumn(name = "userListId"),
			inverseJoinColumns = @JoinColumn(name = "telephoneListId")
			)
	private List<Telephone> telephoneList;

	private byte[] image;

	private String filename;

	@NotEmpty
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

}
