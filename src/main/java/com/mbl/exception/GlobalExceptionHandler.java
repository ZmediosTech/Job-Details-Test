package com.mbl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;

import com.mbl.apiResponse.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

   // Handle all uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
 
       // ex.printStackTrace();
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException e)
	{
		String msg= e.getMessage();
		ApiResponse response= new ApiResponse(null, msg);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
	}
    
    
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<String> handleInternalServerError(InternalServerError e, WebRequest request)
    {
	return new ResponseEntity<String>("Internal Server Error: "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleIllegalAccessException(IllegalAccessException ex, WebRequest request) {
        return new ResponseEntity<>("Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<String> handleNoSuchFieldException(NoSuchFieldException ex, WebRequest request) {
        return new ResponseEntity<>("Field not found: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
}
