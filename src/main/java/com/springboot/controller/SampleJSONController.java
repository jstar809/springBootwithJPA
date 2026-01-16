package com.springboot.controller;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleJSONController {
	Logger log =LogManager.getLogger();
	
	@GetMapping("/helloArr")
	public String[] helloArr() {
		
		log.info("helloArr");
		
		return new String[] {"t" , "s"};
		
	}
	
	
}
