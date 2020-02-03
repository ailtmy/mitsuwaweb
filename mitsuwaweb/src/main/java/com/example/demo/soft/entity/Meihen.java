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
public class Meihen extends Shinseisyo {

	/**
	 * 登記の目的
	 */
	private String mokuteki;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 変更後の事項
	 */
	private String henkojiko;

	/**
	 * 申請人
	 */
	@OneToMany
	@JoinColumn(name = "shinsei_id")
	private List<Gimusya> shinseininList;

	/**
	 * 登録免許税合計
	 */
	private Long toumenGoukei;

	/**
	 * 減税根拠条文
	 */
	private String jyobun;

}
