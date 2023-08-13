package com.henry.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.henry.demo.domain.Role;
import com.henry.demo.domain.RoleEnum;
import com.henry.demo.domain.User;
import com.henry.demo.dto.SignupRequestDTO;
import com.henry.demo.repository.RoleRepository;
import com.henry.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public void createUser(SignupRequestDTO signUpRequest) {

		User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		String strRole = signUpRequest.getRole();
		Role role = new Role();

		if (strRole == null) {
			role = roleRepository.findByName(RoleEnum.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		} else {
			switch (strRole) {
			case "super_admin":
				role = roleRepository.findByName(RoleEnum.ROLE_SUPER_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			case "normal_admin":
				role = roleRepository.findByName(RoleEnum.ROLE_NORMAL_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			default:
				role = roleRepository.findByName(RoleEnum.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			}
		}

		user.setUserRole(role);
		userRepository.save(user);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByUsername(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
