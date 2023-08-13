package com.henry.demo.dto;

public class TodoResponseDTO {

	private int id;
	private String name;
	private String description;
	private String createdBy;
	private String updatedBy;
	private String created;
	private String updated;
	private boolean isCompleted;

	public TodoResponseDTO() {
	}

	public TodoResponseDTO(int id,String name, String description, String createdBy, String updatedBy, String created,
			String updated,boolean isCompleted) {
		super();
		this.id=id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.created = created;
		this.updated = updated;
		this.isCompleted=isCompleted;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

}
