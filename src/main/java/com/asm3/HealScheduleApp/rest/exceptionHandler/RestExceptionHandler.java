package com.asm3.HealScheduleApp.rest.exceptionHandler;

import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.Response;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {


	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exc){

		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"login unsuccessful.","Invalid email or password.");

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception exc){

		Response error = new Response(HttpStatus.BAD_REQUEST.value(),exc.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
