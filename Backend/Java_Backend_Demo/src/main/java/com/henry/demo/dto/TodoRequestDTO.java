package com.henry.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TodoRequestDTO {
	@NotNull
	@Min(0)
	private int id;
	@NotBlank
	private String name;
	private String description;
	private String createdBy;
	private String updatedBy;
	
	@JsonProperty
	private boolean isCompleted;

	public TodoRequestDTO() {
	}

	public TodoRequestDTO(String name, String description, String createdBy, String updatedBy, boolean isCompleted) {
		super();
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isCompleted = isCompleted;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
