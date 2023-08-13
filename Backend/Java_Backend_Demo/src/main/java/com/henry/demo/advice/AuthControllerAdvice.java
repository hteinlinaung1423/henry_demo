package com.henry.demo.advice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.henry.demo.dto.ErrorResponseDTO;
import com.henry.demo.exception.ItemNotFoundException;
import com.henry.demo.exception.TokenRefreshException;

@RestControllerAdvice
public class AuthControllerAdvice {

	@ExceptionHandler(value = TokenRefreshException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponseDTO handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
		return new ErrorResponseDTO(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	protected ErrorResponseDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(x -> x.getField() + " " + x.getDefaultMessage()).collect(Collectors.toList());

		return new ErrorResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), new Date(), errors,
				request.getDescription(false));

	}

	@ExceptionHandler(value = ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseDTO handleAllException(ItemNotFoundException ex, WebRequest request) {
		return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
	}
}
