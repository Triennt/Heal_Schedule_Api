package com.asm3.HealScheduleApp.exception;

public class CustomNotFoundException extends RuntimeException {

	public CustomNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public CustomNotFoundException(String message) {
		super(message);

	}

	public CustomNotFoundException(Throwable cause) {
		super(cause);
		
	}

	
}
