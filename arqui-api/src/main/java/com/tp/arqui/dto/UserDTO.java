package com.tp.arqui.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UserDTO {
	
	private Integer id;
	
	private String name;
	
	private String password;

	private String lastName;

	private String address;

	private String phone;

	private String city;
	
	private String province;
	
	private String country;
	
	private String alternativeMail;
	
	private String mail;
	
}
