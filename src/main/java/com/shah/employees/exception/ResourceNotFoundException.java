package com.shah.employees.exception;

/**
 * @author Shahrukh
 */
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
