package com.example.demo.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.demo.entity.TelAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telephone")
@Getter
@Setter
public class Telephone extends TelAudit {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToMany(mappedBy = "telephoneList")
//	private List<User> userList;

}
