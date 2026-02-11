package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(404,ex.getMessage()));
	}
	
	  @ExceptionHandler(UserAlreadyExistsException.class)
	  
	    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(new ApiError(409, ex.getMessage()));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiError(500, "Something went wrong"));
	    }
	    @ExceptionHandler(InvalidCredentialsException.class)
	    public ResponseEntity<ApiError> handleInvalidCredentials(Exception ex){
	    	ApiError error=new ApiError(HttpStatus.UNAUTHORIZED.value(),
	    			ex.getMessage());
	    	return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	    		
	    	
	    }
	}

