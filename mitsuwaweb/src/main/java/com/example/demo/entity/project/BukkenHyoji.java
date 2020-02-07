package com.example.demo.entity.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class BukkenHyoji {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String shikucyoson;

	private String ooaza;

	private String shikichiban;

	private String kanchi;

	private String mansyonmei;

	private String kaokubango;

	private String tatemonoBango;

	private String syurui;

	private String kozo;

	private String biko;

}
