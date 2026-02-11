package com.springboot.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;



public class Custom403Handler implements AccessDeniedHandler {
	private static final Logger log = LoggerFactory.getLogger(Custom403Handler.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		log.error("-------------- access Denined  -------------- ");
		
		
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		//json 요청인지 
		String contentType = request.getHeader("Content-Type");
		boolean isJson = contentType.startsWith("application/json");
		
		log.info("isjson?: {}" , isJson);
		
		if(!isJson) {
			response.sendRedirect("/member/login?error=ACCESS_DENIED");
		}
		
		
	}

}
