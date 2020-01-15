package com.example.demo.entity.customer;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Audit;
import com.example.demo.entity.MailAudit;
import com.example.demo.entity.TelAudit;
import com.example.demo.entity.honninkakunin.HonninKakunin;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Customer extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 名前（生年月日、カナでユニーク）
	 */
	private String name;

	/**
	 * フリガナ（全角カナ）
	 */
	private String kana;

	/**
	 * 生年月日・設立年月日 ！！和暦にしたい
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	/**
	 * メール（ユニーク）
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "mail_id")
	private List<MailAudit> mailList;

	/**
	 * 電話！！manytomanyである
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "tel_id")
	private List<TelAudit> telephoneList;

	/**
	 * ファイル
	 */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<CustomerFile> customerFileList;

	/**
	 * 顧客メモ(特徴・注意点等）
	 */
	private String memo;

	/**
	 * 案件リスト　manytomany
	 */
//	private List<Project> projectList;

	/**
	 * 本人確認リスト（住所・商業登記事項）onetomany
	 */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<HonninKakunin> honninKakuninList;

	/**
	 * 代理人・代表者リスト　manytomany
	 */
//	@ManyToMany
//	private List<Customer> dairininList;

	/**
	 * 贈答品リスト（年賀状、暑中見舞、中元歳暮 onetomany
	 */
//	private List<Present> presentList





}
