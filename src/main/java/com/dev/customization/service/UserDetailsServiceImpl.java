package com.dev.customization.service;

import com.dev.customization.entity.UserCredentials;
import com.dev.customization.repository.UserCredentialsRepo;
import com.dev.customization.configuration.UserInfoConfig;
import com.dev.customization.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserCredentialsRepo userCredentialsRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserCredentials> user = Optional.ofNullable(userCredentialsRepo.findByEmail(email));
		
		return user.map(UserInfoConfig::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
	}
}