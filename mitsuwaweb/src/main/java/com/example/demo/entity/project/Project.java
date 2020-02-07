package com.example.demo.entity.project;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.entity.Audit;
import com.example.demo.entity.jikenbo.Jikenbo;
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
	 * 登記所
	 */
	@ManyToMany
	private List<Tokisyo> tokisyoList;

	/**
	 * 備考
	 */
	private String memo;

	/**
	 * 事件簿
	 */
	@OneToMany
	private List<Jikenbo> jikenboList;

	/**
	 * ファイル
	 */
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<ProjectFile> projectFileList;
}
