package com.springboot.controller.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestAdvice {
	Logger log = LogManager.getLogger();
	
	@ExceptionHandler(BindException.class)
	//@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<Map<String ,String>> handleBindEx( BindException e){
		
		log.error(e);
		
		Map<String ,String> errorMap = new HashMap<>();
		
		if(e.hasErrors()) {
			e.getBindingResult().getFieldErrors().forEach((error)->{
				errorMap.put(error.getField(), error.getCode());
			});
		}
		
		
		
		return ResponseEntity.badRequest().body(errorMap);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Map<String ,String>> handleSQLIntegrityConstraintViolationEx (SQLIntegrityConstraintViolationException e){
		
		log.error(e);
		
		Map<String,String> errorMap = new HashMap<>();
		errorMap.put("time", ""+System.currentTimeMillis());
		errorMap.put("msg","constaraint Fails");
		
		return ResponseEntity.badRequest().body(errorMap);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Map<String ,String>> handleNoSuchEx (NoSuchElementException e) {
		log.error(e);
		
		Map<String ,String> errorMap = new HashMap<>();
		
		errorMap.put("time", ""+System.currentTimeMillis());
		errorMap.put("msg","No Such Element");
		
		return  ResponseEntity.badRequest().body(errorMap);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Map<String , String>> handleEmptyResultDataAccessEx(EmptyResultDataAccessException e){
		
		log.error(e);
		
		Map<String , String> errorMap = new HashMap<>();
		errorMap.put("time", ""+System.currentTimeMillis());
		errorMap.put("msg","No Such Element");
		
		return ResponseEntity.badRequest().body(errorMap);
	}
	
}
