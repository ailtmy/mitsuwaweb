package com.example.demo.soft.entity;

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
public class FuzokuTatemono {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 符号
	 */
	private String fuzokufugo;

	/**
	 * 種類
	 */
	private String fuzokusyurui;

	/**
	 * 構造
	 */
	private String fuzokukozo;

	/**
	 * 床面積
	 */
	private String fuzokuyukamenseki;

	/**
	 * 備考
	 */
	private String fuzokubiko;

	public FuzokuTatemono() {
		super();
	}

	public FuzokuTatemono(FuzokuTatemono fuzoku) {
		this.fuzokufugo = fuzoku.fuzokufugo;
		this.fuzokusyurui = fuzoku.fuzokusyurui;
		this.fuzokukozo = fuzoku.fuzokukozo;
		this.fuzokuyukamenseki = fuzoku.fuzokuyukamenseki;
		this.fuzokubiko = fuzoku.fuzokubiko;
	}
}
