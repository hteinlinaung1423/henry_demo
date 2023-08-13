package com.henry.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.henry.demo.domain.Todo;
import com.henry.demo.domain.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
	
    public List<Todo> findByUser(User user);
    
	Optional<Todo> findByName(String name);
	
    @Query("SELECT t FROM Todo t ORDER BY t.createdDate DESC")
    public List<Todo> findAllTodoOrderByCreatedDateDesc();
}
