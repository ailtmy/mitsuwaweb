package com.example.demo.entity.jikenbo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Audit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Jikenbo extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public static enum Syubetsu {
		不動産登記, 商業法人登記, 家事裁判書類作成, 財団の登記, 抵当証券の交付, 債権譲渡登記,
		動産譲渡登記, 公共嘱託登記, その他の登記, 筆界特定, 供託, 審査請求, 民事裁判書類作成,
		簡裁訴訟代理, 裁判外和解, 国籍関係書類作成, 検察提出書類作成, 新規成年後見等業務,
		継続成年後見等業務, 新規未成年後見等業務, 継続未成年後見等業務, 新規任意後見任意代理業務,
		継続任意後見任意代理業務, 新規財産管理遺産承継死後事務等業務, 継続財産管理遺産承継死後事務等業務,
		新規不在者相続財産管理人業務, 継続不在者相続財産管理人業務, 継続的相談, 個別的相談, その他業務
	}

	/**
	 * 受任年月日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate jyuninDate;

	/**
	 * 事件名・件名
	 */
	private String jikenName;

	/**
	 * 依頼者の住所・氏名
	 */
	@OneToMany
	private List<Iraisya> iraisyaList;

	/**
	 * 事件番号
	 */
	private String jikenNumber;

	/**
	 * 件数
	 */
	private Integer kensu;

	/**
	 * 事件種類
	 */
	@Enumerated(EnumType.STRING)
	private Syubetsu syubetsu;

}
