package com.example.demo.entity.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	 * 支店。営業所リスト　onetomany
	 */
//  private List<Branch> branchList;

}
