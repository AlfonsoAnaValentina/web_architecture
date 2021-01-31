package com.tp.arqui.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tp.arqui.config.JwtTokenUtil;
import com.tp.arqui.config.tpUserDetails;
import com.tp.arqui.dto.LoginResp;
import com.tp.arqui.model.JwtRequest;
import com.tp.arqui.model.JwtResponse;
import com.tp.arqui.model.UserModel;
import com.tp.arqui.service.IUserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		UserDetails userDetails = new tpUserDetails();
		((tpUserDetails) userDetails).setUserName(authenticationRequest.getUsername());
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		LoginResp a = new LoginResp();
		
		try {
			a.setToken(token);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			a.setUser( userService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword()) );
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ResponseEntity.ok(a);
	}

	private void authenticate(String username, String password) throws Exception {
		UserModel response = userService.login(username, password);
			System.out.println("login ok");

		
/*		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}*/
	}
}
