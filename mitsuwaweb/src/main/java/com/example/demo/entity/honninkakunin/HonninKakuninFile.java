package com.example.demo.entity.honninkakunin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class HonninKakuninFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fileName;

	private byte[] file;

	@ManyToOne
	@JoinColumn(name = "kakunin_syorui_id")
	private KakuninSyorui kakuninSyorui;

}
