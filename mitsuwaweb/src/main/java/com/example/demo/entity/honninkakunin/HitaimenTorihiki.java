package com.example.demo.entity.honninkakunin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class HitaimenTorihiki {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 書類受領日
	 */
	private LocalDate jyuryo;

	/**
	 * 送付日
	 */
	private LocalDate sofu;

	/**
	 * 返信日
	 */
	private LocalDate henshin;

	/**
	 * 意思確認方法
	 */
	private String ishikakunin;

	/**
	 * 確認日時
	 */
	private LocalDateTime ishikakuninDate;


	@OneToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
