package com.henry.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.demo.domain.Role;
import com.henry.demo.domain.RoleEnum;
import com.henry.demo.domain.User;
import com.henry.demo.dto.JwtResponseDTO;
import com.henry.demo.dto.LoginRequestDTO;
import com.henry.demo.dto.MessageResponseDTO;
import com.henry.demo.dto.SignupRequestDTO;
import com.henry.demo.jwt.JwtUserDetailsImpl;
import com.henry.demo.jwt.JwtUtil;
import com.henry.demo.repository.RoleRepository;
import com.henry.demo.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);

		JwtUserDetailsImpl userDetails = (JwtUserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponseDTO(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), jwt, roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		String strRole = signUpRequest.getRole();
		Role role = new Role();

		if (strRole == null) {
			role = roleRepository.findByName(RoleEnum.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		} else {
			switch (strRole) {
			case "admin":
				role = roleRepository.findByName(RoleEnum.SUPER_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			case "mod":
				role= roleRepository.findByName(RoleEnum.NORMAL_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			default:
				role = roleRepository.findByName(RoleEnum.USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			}
		}

		user.setUserRole(role);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseDTO("User registered successfully!"));
	}
}
