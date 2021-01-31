package com.tp.arqui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class UserModel {

	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String password;
	
	private String name;
	
	@Column(name = "last_name")
	private String lastName;

	private String address;

	private String phone;

	private String city;

	private String province;

	private String country;
	
	@Column(name = "alternative_mail")
	private String alternativeMail;

	private String mail;
}
