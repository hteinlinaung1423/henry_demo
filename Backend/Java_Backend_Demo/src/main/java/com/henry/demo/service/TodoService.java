package com.henry.demo.service;

import java.util.List;

import com.henry.demo.dto.TodoRequestDTO;
import com.henry.demo.dto.TodoResponseDTO;

public interface TodoService {
	public void createTodo(TodoRequestDTO item);

	public TodoResponseDTO getTodoById(int id);

	public void updateTodo(TodoRequestDTO item);

	public void deleteTodo(int id);

	public List<TodoResponseDTO> getTodoByUser(String user_id);

	public List<TodoResponseDTO> getAllTodoList();
}
