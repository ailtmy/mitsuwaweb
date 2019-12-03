package com.example.demo.entity.honninkakunin;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 住所種類
	 */
	private String addrKind;

	/**
	 * 郵便番号
	 */

	private String zipCode;

	/**
	 * 住所
	 */
	private String addr;

	/**
	 * 住定日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate addrDate;

	@ManyToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
