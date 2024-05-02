package com.lloyds.data.job.configuration.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DataConfigControllerAdvice extends  ResponseEntityExceptionHandler {
	
	
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus((HttpStatus.INTERNAL_SERVER_ERROR))
	@ResponseBody
    public String handleException( IOException ex) {
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
    public String handleNullPointerException(NullPointerException ex) {
    	return ex.getLocalizedMessage();
    }

	/*
	 * @ExceptionHandler(FileNotFoundException.class) public ResponseEntity<String>
	 * handleUserNotFoundException(FileNotFoundException ex) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " +
	 * ex.getMessage()); }
	 * 
	 * @ExceptionHandler(DataFormatException.class) public ResponseEntity<String>
	 * handleDataFormatException(DataFormatException ex) { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data format error: " +
	 * ex.getMessage()); }
	 */

}
