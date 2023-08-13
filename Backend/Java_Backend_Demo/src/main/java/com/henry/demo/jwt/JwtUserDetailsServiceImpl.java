package com.henry.demo.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.henry.demo.domain.User;
import com.henry.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	  @Autowired
	  UserRepository userRepository;

	  @Override
	  @Transactional
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

	    return JwtUserDetailsImpl.build(user);
	  }

	}
