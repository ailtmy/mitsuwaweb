package com.example.demo.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.Audit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Shinseisyo extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 件名
	 */
	private String kenmei;

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

	public List<ShinseiBukken> getShinseiBukkenList() {
		// TODO 自動生成されたメソッド・スタブ
		return new ArrayList<ShinseiBukken>();
	}

}
