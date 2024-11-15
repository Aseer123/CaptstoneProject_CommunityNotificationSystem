package com.communityapp.notification.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.communityapp.notification.auth.model.User;
import com.communityapp.notification.auth.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public String validateUser(User user) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (auth.isAuthenticated()) {

			return jwtService.createToken(user.getUsername());
		}
		return null;
	}

	public boolean validateToken(String token) {
		jwtService.validateToken(token);
		return true;
	}
}
