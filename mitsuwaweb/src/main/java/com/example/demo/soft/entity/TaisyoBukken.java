package com.example.demo.soft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class TaisyoBukken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String bukkenShitei = "所在";

	private String bukkenKubun; //土地　建物

	private String chibanKuiki;

	private String chibanKaokuBango;

	private String bulkenJyotai; //既存　閉鎖

	private String mokuteki;

	private String yoshiKubun; //甲区　乙区

	private String bangoShitei = "受付番号";

	private String uketsukeDate;

	private Integer uketsukeBango;

	private String Dojyuni;

}
