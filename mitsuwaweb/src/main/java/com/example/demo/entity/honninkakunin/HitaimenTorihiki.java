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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate jyuryo;

	/**
	 * 送付日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate sofu;

	/**
	 * 郵便問い合わせ番号
	 */
	private String registeredNumber;

	/**
	 * 返信日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate henshin;

	/**
	 * 意思確認方法
	 */
	private String ishikakunin;

	/**
	 * 確認日時
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime ishikakuninDate;


	@OneToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
