package com.example.demo.entity.project;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class MansyonLot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 家屋番号
	 */
	private String roomNo;

	/**
	 * 階数
	 */
	private String kaisu;

	/**
	 * 部屋タイプ
	 */
	@OneToOne
	private MansyonRoomType roomType;

	/**
	 * 買主
	 */
	@OneToMany
	private List<Kainushi> kainushiList;

	/**
	 * 登記原因
	 */
	private String genin;

	/**
	 * 申請日
	 */
	private String shinseibi;

	/**
	 * 担保権者
	 */
	@OneToMany
	private List<Tanpokensya> tanpoList;

	/**
	 * 完了処理
	 */
//	private Kanryosyori kanryosyori;

	/**
	 * 進捗状況
	 */
//	private Shincyoku shincyoku;

	/**
	 * 請求処理
	 */
//	private Seikyu seikyu;


}
