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
public class Shikichiken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 土地の符号
	 */
	private String fugo;

	/**
	 * 所在地番
	 */
	private String syozaiChiban;

	/**
	 * 地目
	 */
	private String chimoku;

	/**
	 * 地積
	 */
	private String chiseki;

	/**
	 * 敷地権の種類
	 */
	private String shikichiSyurui;

	/**
	 * 敷地権の割合
	 */
	private String shikichiWariai;

	/**
	 * 備考
	 */
	private String biko;
}
