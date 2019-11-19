package com.example.demo.entity.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.demo.entity.TelAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telephone")
@Getter
@Setter
public class Telephone extends TelAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(mappedBy = "telephoneList")
	private List<User> userList;

}
