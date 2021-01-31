package com.tp.arqui.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class tpUserDetails implements UserDetails {
	
	private String userName;
	
	public tpUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
