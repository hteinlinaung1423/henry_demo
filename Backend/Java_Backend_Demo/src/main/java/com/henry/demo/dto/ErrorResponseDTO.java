package com.henry.demo.dto;

import java.util.Date;
import java.util.List;

public class ErrorResponseDTO {
	private int statusCode;
	private Date timestamp;
	private String message;
	private List<String> messages;
	private String description;

	public ErrorResponseDTO(int statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
	
	public ErrorResponseDTO(int statusCode, Date timestamp, List<String> messages, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.messages = messages;
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
}
