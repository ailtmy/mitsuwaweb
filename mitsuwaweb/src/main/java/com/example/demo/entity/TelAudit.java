package com.example.demo.entity;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class TelAudit extends Audit {

	private String phoneKind;

	private String phoneNumber;
}
