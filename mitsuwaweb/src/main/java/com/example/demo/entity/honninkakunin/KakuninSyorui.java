package com.example.demo.entity.honninkakunin;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class KakuninSyorui {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 書類名
	 */
	private String syoruiName;

	/**
	 * 記号番号
	 */
	private String syoruiKigou;

	/**
	 * 交付日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate syoruiKoufubi;
	/**
	 * 有効期限
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate syoruiYukokigen;

	/**
	 * 発行者名
	 */
	private String syoruiHakkosya;

	/**
	 * 確認書類ファイル
	 */
	@OneToMany(mappedBy = "kakuninSyorui", cascade = CascadeType.ALL)
	private List<HonninKakuninFile> kakuninSyoruiFileList;

	@ManyToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
