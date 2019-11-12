package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table
@Getter
@Setter
public class Customer {

	public static enum PersonDivision {
		個人, 法人
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String kana;

	/**
	 * 生年月日・設立年月日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	/**
	 * 個人・法人区分
	 */
	@Enumerated(EnumType.STRING)
	private PersonDivision person;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<CustomerMail> mailList;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CustomerTel> telephoneList;

	/**
	 * 案件リスト
	 */
//	private List<Project> projectList;

	/**
	 * 本人確認リスト（住所・商業登記事項）
	 */
//	private List<HonninKakunin> honninkakuninList;

	/**
	 * 支店。営業所リスト
	 */
//  private List<Branch> branchList;

	/**
	 * 代理人・代表者リスト
	 */
	@ManyToMany
	private List<Customer> dairininList;

	/**
	 * 贈答品リスト（年賀状、暑中見舞、中元歳暮
	 */
//	private List<Present> presentList



// 名刺等ファイル






}
