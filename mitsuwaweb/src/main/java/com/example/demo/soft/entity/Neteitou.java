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
public class Neteitou extends Shinseisyo {

	/**
	 * 目的
	 */
	private String mokuteki;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 極度額
	 */
	private String saikengaku;

	/**
	 * 債権の範囲
	 */
	private String saikenhanni;

	/**
	 * 確定期日
	 */
	private String kakuteikijitsu;

	/**
	 * 債務者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Saimusya> saimusyaList;

	/**
	 * 根抵当権者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Kenrisya> neteitoukensyaList;

	/**
	 * 設定者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Gimusya> gimusyaList;

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

}
