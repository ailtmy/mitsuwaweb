package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.Audit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Hozon extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 件名
	 */
	private String kenmei;

	/**
	 * 原因
	 */
	private String genin;

	/**
	 * 所有者
	 */
	@OneToMany
	@JoinColumn(name = "Kenrisya_id")
	private List<Kenrisya> syoyusya;

	/**
	 * 添付書類
	 */
	@OneToOne
	private Tempsyorui tempsyorui;

	/**
	 * 申請年月日
	 */
	private String date;

	/**
	 * 登記所
	 */
	@OneToOne
	@JoinColumn(name = "tokisyo_id")
	private Tokisyo tokisyo;

	/**
	 * 課税価格
	 */
	@OneToOne
	@JoinColumn(name = "kazeikakaku_id")
	private Kazeikakaku kazeikakaku;

	@OneToOne
	@JoinColumn(name = "tourokumenkyozei_id")
	/**
	 * 登録免許税
	 */
	private Tourokumenkyozei tourokumenkyozei;

	/**
	 * 対象物件
	 */
	@ManyToMany
	private List<ShinseiBukken> shinseiBukkenList;

}
