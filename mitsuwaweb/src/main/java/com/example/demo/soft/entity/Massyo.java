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
public class Massyo extends Shinseisyo {

	/**
	 * 登記の目的
	 */
	private String mokuteki;

	/**
	 * 抹消すべき登記　元号　年　月　日
	 * 受付番号
	 */
	private String massyoGengo;

	private String massyoYear;

	private String massyoMonth;

	private String massyoDay;

	private String massyoUkeban;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 権利者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Kenrisya> kenrisyaList;

	/**
	 * 義務者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Gimusya> gimusyaList;

	/**
	 * 登録免許税合計
	 */
	private Long toumenGoukei;

	/**
	 * 減税根拠条文
	 */
	private String jyobun;

}
