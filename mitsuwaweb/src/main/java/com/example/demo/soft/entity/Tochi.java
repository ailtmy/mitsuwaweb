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
public class Tochi extends ShinseiBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 物件種別
	 */
	private final String BUKKENSYUBETSU = "土地";

	/**
	 * 地番区域情報　所在
	 */
	private String syozai;

	/**
	 * 地番家屋番号情報　ハイフン方式
	 */
	private String chibanKaokuBangoJyoho;

	/**
	 * 物件区分
	 */
	private final String BUKKENKUBUN = "土地";

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
	public String getChibanKuikiJyoho() {
		return syozai;
	}

}
