package com.henry.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.demo.dto.MessageResponseDTO;
import com.henry.demo.dto.TodoRequestDTO;
import com.henry.demo.exception.ItemNotFoundException;
import com.henry.demo.service.TodoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/todo")
public class TodoController {

	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

	@Autowired
	TodoService todoService;

	@PostMapping("/create")
	public ResponseEntity<?> createTodo(@Valid @RequestBody TodoRequestDTO todoRequest, Authentication auth)
			throws ItemNotFoundException {
		try {
			todoRequest.setCreatedBy(auth.getName());
			todoService.createTodo(todoRequest);
			return ResponseEntity.ok(new MessageResponseDTO("Todo item created successfully!"));

		} catch (ItemNotFoundException ex) {
			logger.error("Error: {}", ex.getMessage());
			throw new ItemNotFoundException(ex.getMessage());
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateTodo(@Valid @RequestBody TodoRequestDTO todoRequest, Authentication auth)
			throws ItemNotFoundException {
		try {
			todoRequest.setUpdatedBy(auth.getName());
			todoService.updateTodo(todoRequest);
			return ResponseEntity.ok(new MessageResponseDTO("Todo item updated successfully!"));

		} catch (ItemNotFoundException ex) {
			logger.error("Error: {}", ex.getMessage());
			throw new ItemNotFoundException(ex.getMessage());
		}
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> GetAllItems() {
		return ResponseEntity.ok(todoService.getAllTodoList());
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> DelteItem(@PathVariable int id) {
		todoService.deleteTodo(id);
		return ResponseEntity.ok(new MessageResponseDTO("Todo item deleted successfully!"));
	}

	@GetMapping("/myitem")
	@PreAuthorize("hasRole('USER') or hasRole('NORMAL_ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> GetMyItem(Authentication auth) {
		return ResponseEntity.ok(todoService.getTodoByUser(auth.getName()));
	}

}
