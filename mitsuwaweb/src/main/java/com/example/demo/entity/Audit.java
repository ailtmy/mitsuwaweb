package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Audit {

	/**
	 * 作成者
	 */
	@CreatedBy
	private String createBy;

	/**
	 * 作成日時
	 */
	@CreatedDate
	private LocalDateTime createdDate;

	/**
	 * 最終更新者
	 */
	@LastModifiedBy
	private String lastModfiedBy;

	/**
	 * 最終更新日時
	 */
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
