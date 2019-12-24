package com.example.demo.soft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class IttouTatemono extends ShinseiBukken {

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

	@Override
	public String getBukkenSyubetsu() {
		return "区分建物（一棟）";
	}

	@Override
	public String getChibanKuikiJyoho() {
		return shikucyoson + ooaza;
	}

	@Override
	public String getChibanKaokubangoJyoho() {
		String str = shikichiban.replace("番地", "－");
		if(str.endsWith("－")) {
			str = str.replace("－", "");
		}
		return str;
	}

	@Override
	public String getBukkenKubun() {
		return "一棟";
	}

}
