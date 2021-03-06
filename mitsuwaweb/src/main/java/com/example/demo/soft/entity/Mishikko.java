package com.example.demo.soft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Mishikko extends Shinseisyo {


//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

//	/**
//	 * 件名
//	 */
//	private String kenmei;

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
	 * 対象物件
	 */
	@ManyToMany
	private List<TaisyoBukken> taisyoBukkenList;
}
