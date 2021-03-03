package com.tp.arqui.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp.arqui.dto.UserDTO;
import com.tp.arqui.model.MessageModel;
import com.tp.arqui.model.UserModel;
import com.tp.arqui.model.repository.UserRepository;
import com.tp.arqui.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserModel login(String userMail, String password) {
		List<UserModel> response = userRepository.findByMailAndPassword(userMail, password);
		if (response.isEmpty())
			throw new RuntimeException("Wrong Email or Password");
		else 
			return response.get(0);
	}

	@Override
	public UserModel getUsername(String userMail) {
		UserModel response = userRepository.findByMail(userMail);
		if (response == null)
			throw new RuntimeException("Wrong Email or Password");
		else 
			return response;
	}

	@Override
	public UserDTO createUser(@Valid UserDTO req) {
		UserModel request = modelMapper.map(req, UserModel.class);
		UserModel newUsr = userRepository.save(request);
		req.setId(newUsr.getId());
		return req;
	}

}
