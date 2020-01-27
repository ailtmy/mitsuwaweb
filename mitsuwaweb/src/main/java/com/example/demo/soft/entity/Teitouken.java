package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Teitouken extends Shinseisyo {

	/**
	 * 目的
	 */
	private String mokuteki;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 債権額
	 */
	private String saikengaku;

	/**
	 * 利息
	 */
	private String risoku;

	/**
	 * 損害金
	 */
	private String songaikin;

	/**
	 * 債務者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Saimusya> saimusyaList;

	/**
	 * 抵当権者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Kenrisya> teitoukensyaList;

	/**
	 * 設定者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Gimusya> gimusyaList;

	/**
	 * 添付書類
	 */
	@OneToOne
	private Tempsyorui tempsyoruis;

	/**
	 * 課税価格
	 */
	private Long kazeiGoukei;

	/**
	 * 登録免許税合計
	 */
	private Long toumenGoukei;

	/**
	 * 減税根拠条文
	 */
	private String jyobun;

	/**
	 * 対象物件
	 */





}
