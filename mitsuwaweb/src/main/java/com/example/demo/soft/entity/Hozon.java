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
public class Hozon extends Shinseisyo {

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 所有者
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Kenrisya> syoyusya;

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

}
