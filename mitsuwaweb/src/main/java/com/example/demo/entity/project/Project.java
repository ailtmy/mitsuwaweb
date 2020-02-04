package com.example.demo.entity.project;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Audit;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Tokisyo;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Project extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * プロジェクト名
	 */
	private String name;

	/**
	 * 予定年月日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime projectdatetime;

	/**
	 * 登記所
	 */
	@ManyToMany
	private List<Tokisyo> tokisyoList;

	/**
	 * 対象物件
	 */
	@ManyToMany
	private List<ShinseiBukken> shinseiBukkenList;

	private String memo;

	/**
	 * ファイル
	 */
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<ProjectFile> projectFileList;


}
