package com.hexaware.rentalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling Feedback related errors and returning
 * structured error responses to the client.
 */

@RestControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler({FeedbackNotFoundException.class})
	public ResponseEntity<String> userExceptionHandler()
	{
		return new ResponseEntity<String>("Feedback ID not found, enter a valid Feedback ID", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({PaymentNotFoundException.class})
	public ResponseEntity<String> paymentExceptionHandler()
	{
		return new ResponseEntity<String>("Payment ID not found, enter a valid Payment ID", HttpStatus.NOT_FOUND);
	}
}
