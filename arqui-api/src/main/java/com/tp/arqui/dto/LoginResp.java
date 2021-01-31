package com.tp.arqui.dto;

import com.tp.arqui.model.UserModel;

import lombok.Data;

@Data
public class LoginResp {

	private String token;
	private UserModel user;
}
