package com.cts.microservices.buyerservice.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.microservices.buyerservice.exception.CannotPlaceBidException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String, String> errorMap= new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach((err)->{
			errorMap.put(err.getField(), err.getDefaultMessage());
		});
		return errorMap;
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(CannotPlaceBidException.class)
	public Map<String,String> handleCannotPlaceBidException(CannotPlaceBidException e){
		Map<String, String> errorMap= new HashMap<>();
		errorMap.put("errorMessage", e.getMessage());
		return errorMap;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String,String> handleInvalidArgumentException(HttpMessageNotReadableException e){
		Map<String, String> errorMap= new HashMap<>();
		errorMap.put("errorMessage",e.getMessage());
		return errorMap;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String,String> handleInvalidArgumentException(ConstraintViolationException e){
		Map<String, String> errorMap= new HashMap<>();
		e.getConstraintViolations().forEach((err)->{
			errorMap.put(err.getMessageTemplate(), err.getMessage());
		});
		return errorMap;
	}
	
	
}
