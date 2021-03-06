package com.example.demo.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.example.demo.entity.Audit;
import com.example.demo.entity.MailAudit;
import com.example.demo.entity.TelAudit;
import com.example.demo.entity.honninkakunin.HonninKakunin;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Setter
@Getter
public class User extends Audit {

	public static enum Role {
		ROLE_ADMIN, ROLE_GENERAL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="name", nullable= false, unique=true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "mail_id")
	private List<MailAudit> mailList;

	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "tel_id")
	private List<TelAudit> telephoneList;

	private byte[] image;

	private String filename;

	@NotEmpty
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<HonninKakunin> honninKakuninList;

}
