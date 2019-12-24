package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Tatemono extends ShinseiBukken {

	/**
	 * 所在
	 */
	private String syozai;

	/**
	 * 敷地番
	 */
	private String shikichiban;

	/**
	 * 換地の記載
	 */
	private String kanchi;

	/**
	 * 家屋番号
	 */
	private String kaokuBango;

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
	 * 備考
	 */
	private String biko;

	/**
	 * 附属建物
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_bukken_id")
	private List<FuzokuTatemono> fuzokuTatemono;

	@Override
	public String getBukkenSyubetsu() {
		return "一般建物";
	}

	@Override
	public String getChibanKuikiJyoho() {
		return syozai;
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
		return "建物";
	}

}
