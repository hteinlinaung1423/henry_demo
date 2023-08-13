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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.demo.domain.RefreshToken;
import com.henry.demo.dto.JwtResponseDTO;
import com.henry.demo.dto.LoginRequestDTO;
import com.henry.demo.dto.MessageResponseDTO;
import com.henry.demo.dto.RefreshTokenResponseDTO;
import com.henry.demo.dto.SignupRequestDTO;
import com.henry.demo.exception.TokenRefreshException;
import com.henry.demo.jwt.JwtUserDetailsImpl;
import com.henry.demo.jwt.JwtUtil;
import com.henry.demo.service.RefreshTokenService;
import com.henry.demo.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);

		JwtUserDetailsImpl userDetails = (JwtUserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtResponseDTO(userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), jwt, roles, refreshToken.getToken()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
		if (userService.existsByUsername(signUpRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Email is already in use!"));
		}

		// Create new user's account
		userService.createUser(signUpRequest);

		return ResponseEntity.ok(new MessageResponseDTO("User registered successfully!"));
	}

	@GetMapping("/refreshToken/{rToken}")
	public ResponseEntity<?> refreshtoken(@PathVariable String rToken) {

		RefreshToken refreshToken = refreshTokenService.findByToken(rToken)
				.orElseThrow(() -> new TokenRefreshException(rToken, "Refresh token is not in database!"));

		refreshTokenService.verifyExpiration(refreshToken);
		String token = jwtUtil.generateTokenFromUsername(refreshToken.getUser().getUsername());
		return ResponseEntity.ok(new RefreshTokenResponseDTO(token, refreshToken.getToken()));
	}
}
