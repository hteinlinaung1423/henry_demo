package com.henry.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.demo.dto.TodoResponseDTO;
import com.henry.demo.service.TodoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/secure")
public class SecureContentController {

	@Autowired
	TodoService todoService;

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('NORMAL_ADMIN') or hasRole('SUPER_ADMIN')")
	public String all(Authentication auth) {
		return "All user view. "+auth.getName();
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('NORMAL_ADMIN') or hasRole('SUPER_ADMIN')")
	public String admin() {
		return "Admin  view.";
	}

	@GetMapping("/superadmin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> superadmin() {
		List<TodoResponseDTO> dtoList = todoService.getAllTodoList();
		return ResponseEntity.ok(dtoList);
	}

}
