package com.example.demo.soft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Tochi extends ShinseiBukken {

	/**
	 * 所在
	 */
	private String syozai;

	/**
	 * 地番
	 */
	private String chiban;

	/**
	 * 地目
	 */
	private String chimoku;

	/**
	 * 地積
	 */
	private String chiseki;

	/**
	 * 備考
	 */
	private String biko;

	@Override
	public String getBukkenSyubetsu() {
		return "土地";
	}

	@Override
	public String getChibanKuikiJyoho() {
		return syozai;
	}

	@Override
	public String getChibanKaokubangoJyoho() {
		String str = chiban.replace("番", "－");
		if(str.endsWith("－")) {
			str = str.replace("－", "");
		}
		return str;
	}

	@Override
	public String getBukkenKubun() {
		return "土地";
	}
}
