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
public class IttouTatemono implements ShinseiBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 物件種別
	 */
	private final String BUKKENSYUBETSU = "区分建物（一棟）";

	/**
	 * 地番家屋番号情報
	 */
	private String chibanKaokubango;

	/**
	 * 物件区分
	 */
	private final String BUKKENKUBUN = "一棟";

	/**
	 * 地番区域市区町村
	 */
	private String shikucyoson;

	/**
	 * 地番区域大字
	 */
	private String ooaza;

	/**
	 * 敷地番
	 */
	private String shikichiban;

	/**
	 * 換地等の記載
	 */
	private String kanchi;

	/**
	 * 建物番号
	 */
	private String tatemonoBango;

	/**
	 * 構造
	 */
	private String kozo;

	/**
	 * 床面積
	 */
	private String yukamenseki;

	/**
	 * 備考
	 */
	private String biko;

	/**
	 * 地番区域
	 */
	@Override
	public String getChibanKuikiJyoho() {
		return shikucyoson + ooaza;
	}
}
