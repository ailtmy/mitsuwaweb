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
	private String shikichifugo;

	/**
	 * 所在地番
	 */
	private String shikichisyozaichiban;

	/**
	 * 地目
	 */
	private String shikichichimoku;

	/**
	 * 地積
	 */
	private String shikichichiseki;

	/**
	 * 敷地権の種類
	 */
	private String shikichisyurui;

	/**
	 * 敷地権の割合
	 */
	private String shikichiwariai;

	/**
	 * 備考
	 */
	private String shikichibiko;

	public Shikichiken() {
		super();
	}

	public Shikichiken(Shikichiken shikichi) {
		this.shikichifugo = shikichi.shikichifugo;
		this.shikichisyozaichiban = shikichi.shikichisyozaichiban;
		this.shikichichimoku = shikichi.shikichichimoku;
		this.shikichichiseki = shikichi.shikichichiseki;
		this.shikichisyurui = shikichi.shikichisyurui;
		this.shikichiwariai = shikichi.shikichiwariai;
		this.shikichibiko = shikichi.shikichibiko;
	}
}
