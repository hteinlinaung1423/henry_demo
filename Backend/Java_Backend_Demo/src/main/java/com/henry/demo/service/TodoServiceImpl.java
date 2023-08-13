package com.henry.demo.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henry.demo.repository.TodoRepository;
import com.henry.demo.repository.UserRepository;
import com.henry.demo.domain.Todo;
import com.henry.demo.domain.User;
import com.henry.demo.dto.TodoRequestDTO;
import com.henry.demo.dto.TodoResponseDTO;
import com.henry.demo.exception.ItemNotFoundException;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	TodoRepository todoRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public void createTodo(TodoRequestDTO item) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(item.getCreatedBy()).orElse(null);
		if (user != null) {
			User updatedBy = userRepository.findByUsername(item.getCreatedBy()).orElse(null);
			Todo todo = new Todo(item.getName(), item.getDescription(),
					updatedBy == null ? "" : updatedBy.getUsername(), user, Instant.now(), Instant.now(), false);
			todoRepository.save(todo);
		} else
			throw new ItemNotFoundException("Created user is not in DB!");

	}

	@Override
	public TodoResponseDTO getTodoById(int id) {
		// TODO Auto-generated method stub
		Todo todo = todoRepository.getReferenceById(id);
		if (todo != null) {
			TodoResponseDTO dto = new TodoResponseDTO(todo.getId(), todo.getName(), todo.getDescription(),
					todo.getUser().getUsername(), todo.getUpdatedBy(), todo.getCreatedDate().toString(),
					todo.getUpdatedBy().toString(), todo.isCompleted());

			return dto;
		}
		return null;
	}

	@Override
	public void updateTodo(TodoRequestDTO item) {
		// TODO Auto-generated method stub
		Todo todo = todoRepository.findById(item.getId()).orElse(null);
		User updatedBy = userRepository.findByUsername(item.getUpdatedBy()).orElse(null);
		if (todo != null & updatedBy != null) {
			todo.setName(item.getName());
			todo.setDescription(item.getDescription());
			todo.setUpdatedBy(updatedBy.getUsername());
			todo.setUpdatedDate(Instant.now());
			todo.setCompleted(item.isCompleted());
			todoRepository.save(todo);
		} else
			throw new ItemNotFoundException("Updated user or Todo item is not in DB!");

	}

	@Override
	public void deleteTodo(int id) {
		// TODO Auto-generated method stub
		todoRepository.deleteById(id);
	}

	@Override
	public List<TodoResponseDTO> getTodoByUser(String user_name) {
		// TODO Auto-generated method stub
		List<Todo> todoList = todoRepository.findByUser(userRepository.findByUsername(user_name).get());
		List<TodoResponseDTO> dtoList = new ArrayList<TodoResponseDTO>();
		for (Todo todo : todoList) {
			TodoResponseDTO dto = new TodoResponseDTO(todo.getId(), todo.getName(), todo.getDescription(),
					todo.getUser().getUsername(), todo.getUpdatedBy(), todo.getCreatedDate().toString(),
					todo.getUpdatedDate().toString(), todo.isCompleted());
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<TodoResponseDTO> getAllTodoList() {
		// TODO Auto-generated method stub
		List<Todo> todoList = todoRepository.findAll();
		List<TodoResponseDTO> dtoList = new ArrayList<TodoResponseDTO>();
		for (Todo todo : todoList) {
			TodoResponseDTO dto = new TodoResponseDTO(todo.getId(), todo.getName(), todo.getDescription(),
					todo.getUser().getUsername(), todo.getUpdatedBy(), todo.getCreatedDate().toString(),
					todo.getUpdatedDate().toString(), todo.isCompleted());
			dtoList.add(dto);
		}
		return dtoList;
	}

}
