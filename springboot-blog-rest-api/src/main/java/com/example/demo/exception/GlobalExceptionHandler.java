package com.example.demo.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.payload.ErrorDetails;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// handle specific Exception

	@ExceptionHandler(ResourceNotFoundException.class)

	public ResponseEntity<ErrorDetails> handlesResourseNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(true));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BlogAPIException.class)

	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(true));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}
	// globalException

	@ExceptionHandler(Exception.class)

	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(true));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}
//customized validation Response

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// map Stores the data in key and the value pair
		// hear key is fieldName,and value is massage
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();

			String message = error.getDefaultMessage();

			errors.put(fieldName, message);

		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	
	
	//security exception handler
	
	@ExceptionHandler(AccessDeniedException.class)

	public ResponseEntity<ErrorDetails> handleAccessDeniedException(Exception exception, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(true));

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);

	}
	
	//second Aproch for the MethodArgumentNotValidException
	/*
	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<Object> handleBlogAPIException(MethodArgumentNotValidException exception, WebRequest webRequest) {

		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();

			String message = error.getDefaultMessage();

			errors.put(fieldName, message);

		});
		

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

	}
*/
}
