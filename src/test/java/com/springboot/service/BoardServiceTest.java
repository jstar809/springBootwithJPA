package com.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.dto.BoardDTO;
import com.springboot.dto.PageRequsetDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTest {

	@Autowired
	public BoardService boardService;
	
	/*
	 * @Test public void TestRegister() {
	 * 
	 * log.info(boardService);
	 * 
	 * BoardDTO boardDTO = BoardDTO.builder() .title("sampleTitle...")
	 * .writer("user..") .content("sample content") .build();
	 * 
	 * log.info(boardService.register(boardDTO)); }
	 * 
	 * @Test public void testModify() {
	 * 
	 * BoardDTO boardDTO = BoardDTO.builder() .bno(31L) .title("제목 수정")
	 * .content("내용수정") .build();
	 * 
	 * boardService.modify(boardDTO);
	 * 
	 * }
	 */
	
	/*@Test
	public void testRemove() {
		boardService.remove(31L);
	}*/
	
	/*
	 * @Test public void testList() {
	 * 
	 * PageRequsetDTO dto = PageRequsetDTO.builder() .page(1) .size(10)
	 * .keyword("title4") .type("wt") .build();
	 * 
	 * log.info(boardService.list(dto)); }
	 */
}
