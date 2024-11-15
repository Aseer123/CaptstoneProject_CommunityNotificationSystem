package com.communityapp.notification.auth.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.communityapp.notification.auth.model.User;

public class CustomUserDetails implements UserDetails{

	User userFound=null;
	
	
	CustomUserDetails(User user){
		this.userFound=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userFound.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userFound.getUsername();
	}
	

}
