package com.springboot.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.springboot.domain.Board;
import com.springboot.domain.Reply;

@SpringBootTest
public class ReplyRepositoryTest {
	Logger log =LogManager.getLogger(ReplyRepositoryTest.class);
	
	@Autowired
	ReplyRepository replyRepository;
	
	/*
	 * @Test public void testInsert() {
	 * 
	 * Board board = Board.builder().bno(10L).build();
	 * 
	 * for(int i=0 ; i<10 ;i++) {
	 * 
	 * Reply reply = Reply.builder() .replyer("replyer1") .replyText("댓글..")
	 * .board(board) .build();
	 * 
	 * replyRepository.save(reply); }
	 * 
	 * }
	 */
	
	@Test
	public void selectAll() {
		replyRepository.listOfBoard(10L, PageRequest.of(0, 10)).getContent()
		.forEach((reply)->{
			log.info(reply);
		});;
		
		
		
	}
	
	/*
	 * @Test //보드 필드가 lazy 방식인데 이떄 baord 호출 하면 트랜잭션이 진작에 끝나 오류발생 public void
	 * selectAllErrorTest() {
	 * 
	 * 
	 * replyRepository.listOfBoard(10L, PageRequest.of(0, 10)).getContent()
	 * .forEach((reply)->{ log.info(reply.getBoard()); });;
	 * 
	 * 
	 * }
	 */
}
