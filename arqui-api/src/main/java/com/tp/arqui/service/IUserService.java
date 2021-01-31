package com.tp.arqui.service;

import javax.validation.Valid;

import com.tp.arqui.dto.UserDTO;
import com.tp.arqui.model.UserModel;

public interface IUserService {

	UserModel login(String userMail, String password);

	UserDTO createUser(@Valid UserDTO req);

	UserModel getUsername(String userMail);

}
