package com.test.spring.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.spring.pojo.User;
import com.test.spring.repository.UserRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> opt = userRepository.findUserByEmail(email);

		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("User with email: " + email + " not found !");
		}

		User user = opt.get();

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
	}

	public void createUser(UserDetails user) {
		userRepository.save((User) user);
	}

}