package com.example.demo.entity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class MailAudit extends Audit {

	private String mailKind;

	@Email
	private String mailAddr;

}
