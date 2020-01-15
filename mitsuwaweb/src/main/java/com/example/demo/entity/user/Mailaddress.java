package com.example.demo.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.demo.entity.MailAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mailaddress")
@Getter
@Setter
public class Mailaddress extends MailAudit {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToOne
//	@JoinColumn(name = "userId")
//	private User user;

}
