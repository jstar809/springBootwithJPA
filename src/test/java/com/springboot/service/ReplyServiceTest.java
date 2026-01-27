package com.springboot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.dto.ReplyDTO;

@SpringBootTest
public class ReplyServiceTest {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	public ReplyService replyService;
	
	@Test
	public void registerTest() {
		
		ReplyDTO dto = ReplyDTO.builder().replyText("test").bno(1L).replyer("replayer").build();
						
		log.info(replyService.register(dto));
		
	}
	
}
