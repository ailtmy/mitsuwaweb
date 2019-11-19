package com.example.demo.entity.honninkakunin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.entity.Audit;
import com.example.demo.entity.customer.Customer;
import com.example.demo.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class HonninKakunin extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 確認者
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 顧客 manytoone
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	/**
	 * 対面取引
	 */
	@OneToOne(mappedBy = "honninKakunin",cascade = CascadeType.ALL)
	private TaimenTorihiki taimen;

//-----------------

//非対面取引クラス----------
	/**
	 * 非対面取引
	 */

	/**
	 * 書類受領日
	 */

	/**
	 * 送付日
	 */

	/**
	 * 返信日
	 */

	/**
	 * 備考
	 */
	private String memo;

	/**
	 * 住所リスト onetomany
	 */
	@OneToMany(mappedBy = "honninKakunin", cascade = CascadeType.ALL)
	private List<CustomerAddress> addressList;

	/**
	 * 本人確認書類
	 */
	@OneToMany(mappedBy = "honninKakunin", cascade = CascadeType.ALL)
	private List<KakuninSyorui> kakuninSyoruiList;


}
