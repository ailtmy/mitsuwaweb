package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class SenyuTatemono extends ShinseiBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 物件種別
	 */
	private final String BUKKENSYUBETSU = "区分建物（専有）";

	/**
	 * 地番家屋番号情報
	 */
	private String chibanKaokubango;

	/**
	 * 物件区分
	 */
	private final String BUKKENKUBUN = "区建";

	/**
	 * 地番区域市区町村
	 */
	private String shikucyoson;

	/**
	 * 地番区域大字
	 */
	private String ooaza;

	/**
	 * 家屋番号
	 */
	private String kaokuBango;

	/**
	 * 建物の番号
	 */
	private String tatemonoBango;

	/**
	 * 種類
	 */
	private String syurui;

	/**
	 * 構造
	 */
	private String kozo;

	/**
	 * 床面積
	 */
	private String yukamenseki;

	/**
	 * 敷地権
	 */
	@OneToMany
	private List<Shikichiken> shikichiken;

	/**
	 * 附属建物
	 */
	@OneToMany
//	private List<FuzokuTatemono> fuzokuTatemono;

	/**
	 * 地番区域情報
	 */
	@Override
	public String getChibanKuikiJyoho() {
		return shikucyoson + ooaza;
	}

}
