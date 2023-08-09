package com.henry.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

	protected final String message = "{\"Message\": \"Your application is running.\"}";

	@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public String home() {
		return message;
	}
}
