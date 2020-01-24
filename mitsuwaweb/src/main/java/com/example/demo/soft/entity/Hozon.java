package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Hozon extends Shinseisyo {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

//	/**
//	 * 件名
//	 */
//	private String kenmei;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 所有者
	 */
	@OneToMany
	@JoinColumn(name = "Shinsei_id")
	private List<Kenrisya> syoyusya;

	/**
	 * 添付書類
	 */
	@OneToOne
	private Tempsyorui tempsyorui;

//	/**
//	 * 申請年月日
//	 */
//	private String date;

//	/**
//	 * 登記所
//	 */
//	@OneToOne
//	@JoinColumn(name = "tokisyo_id")
//	private Tokisyo tokisyo;

	/**
	 * 課税価格
	 */
	private Long kazeiGoukei;

	/**
	 * 課税価格内訳
	 */
	private String kazeiUchiwake;

	/**
	 * 登録免許税合計
	 */
	private Long toumenGoukei;

	/**
	 * 登録免許税内訳
	 */
	private String toumenUchiwake;

	/**
	 * 減税根拠条文
	 */

	private String jyobun;

	/**
	 * 対象物件
	 */
	@ManyToMany
	private List<ShinseiBukken> shinseiBukkenList;

}
