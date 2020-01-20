package com.example.demo.entity.customer;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Company extends Customer {

	/**
	 * 会社法人等番号
	 */
	private String houjinbango;

	/**
	 * 代表者
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private List<Daihyo> daihyosya;

	/**
	 * 支店。営業所リスト　onetomany
	 */
//  private List<Branch> branchList;

	/**
	 * 次回商業申請期限
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nextSyogyoToki;

}
