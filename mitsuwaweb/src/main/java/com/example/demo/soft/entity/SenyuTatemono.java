package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class SenyuTatemono extends ShinseiBukken {

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
	private List<FuzokuTatemono> fuzokuTatemono;

	@Override
	public String getBukkenSyubetsu() {
		return "区分建物（専有）";
	}

	@Override
	public String getChibanKuikiJyoho() {
		return shikucyoson + ooaza;
	}

	@Override
	public String getChibanKaokubangoJyoho() {
		String str = kaokuBango.replace("番", "－");
		str = str.replace("の", "－");
		if(str.endsWith("－")) {
			str = str.replace("－", "");
		}
		return str;
	}

	@Override
	public String getBukkenKubun() {
		return "区建";
	}

}
