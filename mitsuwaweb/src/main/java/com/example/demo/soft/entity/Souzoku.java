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
public class Souzoku extends Shinseisyo {

	/**
	 * 登記の目的
	 */
	private String mokuteki;

	/**
	 * 登記原因
	 */
	private String genin;

	/**
	 * 被相続人
	 */
	private String hisouzokunin;

	/**
	 * 相続人
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Kenrisya> souzokuninList;

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
