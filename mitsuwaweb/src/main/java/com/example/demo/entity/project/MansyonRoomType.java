package com.example.demo.entity.project;

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
public class MansyonRoomType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private String typeName;

	private String yukamenseki;

	private String shikichiWriai;

	private Long kazeiKakaku;

	private String tochiKazei;

	private String tatemonoKazei;

	private Long gezeiTax;

	private Long nashiTax;

	private String tochiTax;

	private String tatemonogenzeiTax;

	private String tatemononashiTax;
}
