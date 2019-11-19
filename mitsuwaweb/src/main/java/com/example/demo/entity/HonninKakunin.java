package com.example.demo.entity;

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
public class HonninKakunin extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 確認者
	 */


	/**
	 * 顧客 manytoone
	 */

//対面取引クラス----------
	/**
	 * 対面取引
	 */

	/**
	 * 確認日時
	 */

	/**
	 * 確認場所
	 */
//-----------------

//非対面取引クラス----------
	/**
	 * 非対面取引
	 */

	/**
	 * 書類受領日
	 */

	/**
	 * 送付日
	 */

	/**
	 * 返信日
	 */

//-----------------


	/**
	 * 備考
	 */


//住所クラス------------------
	/**
	 * 住所リスト onetomany
	 */
//-------------------------------

// 本人確認書類クラス-------------------
	/**
	 * 書類ファイル
	 */

	/**
	 * 書類名
	 */

	/**
	 * 記号番号
	 */

	/**
	 * 交付日
	 */

	/**
	 * 有効期限
	 */

	/**
	 * 発行者名
	 */
//---------------------------


}
