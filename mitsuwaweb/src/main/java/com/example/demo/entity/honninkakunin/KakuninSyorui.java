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
	private String name;

	/**
	 * 記号番号
	 */
	private String kigou;

	/**
	 * 交付日
	 */
	private LocalDate koufubi;

	/**
	 * 有効期限
	 */
	private LocalDate yukokigen;

	/**
	 * 発行者名
	 */
	private String hakkousya;

	/**
	 * 確認書類ファイル
	 */
	@OneToMany(mappedBy = "kakuninSyorui", cascade = CascadeType.ALL)
	private List<HonninKakuninFile> kakuninSyoruiFileList;

	@ManyToOne
	@JoinColumn(name = "honnin_kakunin_id")
	private HonninKakunin honninKakunin;

}
