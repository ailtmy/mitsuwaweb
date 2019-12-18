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
	private String fugo;

	/**
	 * 種類
	 */
	private String syurui;

	/**
	 * 構造
	 */
	private String Kozo;

	/**
	 * 床面積
	 */
	private String yukamenseki;

	/**
	 * 備考
	 */
	private String biko;
}
