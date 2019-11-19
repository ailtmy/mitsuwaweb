package com.example.demo.entity.honninkakunin;

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
public class TaimenTorihiki {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 対面
	 */
	private final String TAIMEN = "対面";

	/**
	 * 確認日時
	 */
	private LocalDateTime kakuninDate;

	/**
	 * 確認場所
	 */
	private String kakuninPlace;

	@OneToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
