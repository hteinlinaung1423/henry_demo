package com.henry.demo.service;

import com.henry.demo.dto.SignupRequestDTO;

public interface UserService {
	public void createUser(SignupRequestDTO signUpRequest);
	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
}
