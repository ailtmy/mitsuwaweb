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
public class ShinseiBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 不動産番号
	 */
	private String fudosanBango;


	/**
	 * 物件種別
	 * @return
	 */
	public String getBukkenSyubetsu() {
		return "";
	}

	/**
	 * 地番区域情報
	 * @return
	 */
	public String getChibanKuikiJyoho() {
		return "";
	}

	/**
	 * 地番家屋番号情報
	 * @return
	 */
	public String getChibanKaokubangoJyoho() {
		return "";
	}

	/**
	 * 物件区分
	 * @return
	 */
	public String getBukkenKubun() {
		return "";
	}
}
