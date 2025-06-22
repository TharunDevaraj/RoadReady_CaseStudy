package com.hexaware.vehicleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling Car Related errors and returning
 * structured error responses to the client.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({CarNotFoundException.class})
	public ResponseEntity<String> userExceptionHandler()
	{
		return new ResponseEntity<String>("Car ID not found, enter a valid Car ID", HttpStatus.NOT_FOUND);
	}
}