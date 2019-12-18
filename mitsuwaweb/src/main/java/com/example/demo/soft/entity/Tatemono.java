package com.example.demo.soft.entity;

import java.util.List;

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
public class Tatemono implements ShinseiBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 物件種別
	 */
	private final String BUKKENSYUBETSU = "一般建物";

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
	private final String BUKKENKUBUN = "建物";

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
	private List<FuzokuTatemono> fuzokuTatemono;

	@Override
	public String getChibanKuikiJyoho() {
		return syozai;
	}

}
