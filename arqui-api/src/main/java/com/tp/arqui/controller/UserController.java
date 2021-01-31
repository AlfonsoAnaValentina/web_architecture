package com.tp.arqui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.dto.UserDTO;
import com.tp.arqui.model.UserModel;
import com.tp.arqui.service.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@ResponseBody
	public UserDTO createUser(@Valid @RequestBody UserDTO req) {
		return userService.createUser(req);
	}
	
	@GetMapping("/login")
	@ResponseBody
	public UserModel getProduct(@RequestParam String userMail, @RequestParam String password) {
		UserModel response = userService.login(userMail, password);
		return response;
	}

}
