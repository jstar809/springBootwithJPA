package com.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.dto.BoardListAllReplyDTO;
import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;

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
	
	/*
	 * @Test public void testRemove() { boardService.remove(142L); }
	 */
	
	/*
	 * @Test public void testList() {
	 * 
	 * PageRequsetDTO dto = PageRequsetDTO.builder() .page(1) .size(10)
	 * .keyword("title4") .type("wt") .build();
	 * 
	 * log.info(boardService.list(dto)); }
	 */
	
	/*
	 * @Test public void testRegisterBoardWithImage() { BoardDTO boardDto =
	 * BoardDTO.builder() .title("title999") .content("content999")
	 * .writer("writer999") .fileNames(Arrays.asList( UUID.randomUUID().toString()
	 * +"_999-1.png" , UUID.randomUUID().toString() +"_999-2.png" ,
	 * UUID.randomUUID().toString() +"_999-3.png" )) .build();
	 * 
	 * log.info(boardService.register(boardDto));
	 * 
	 * }
	 */
	
	/*
	 * @Test public void testReadOneWithImage() {
	 * 
	 * BoardDTO dto = boardService.readOne(42L); log.info(dto);
	 * dto.getFileNames().forEach((i)->{ log.info(i); });
	 * 
	 * }
	 */
	
	/*
	 * @Test public void testModifyWithImage() {
	 * 
	 * BoardDTO boardDTO = BoardDTO.builder() .bno(142L) .content("conup")
	 * .title("uptitle") .fileNames(Arrays.asList( UUID.randomUUID().toString() +
	 * "_8881.png", UUID.randomUUID().toString() + "_8882.png",
	 * UUID.randomUUID().toString() + "_8883.png" )) .build();
	 * 
	 * 
	 * boardService.modify(boardDTO); }
	 */
	
	
	
	
	@Test
	public void testListWithAll() {
		PageResponseDTO<BoardListAllReplyDTO> result = boardService.listWithAll(PageRequsetDTO.builder().build());
		
		result.getDtoList().forEach((d)->{
			log.info(d);
			if(d.getBoardImage() != null) {
				d.getBoardImage() .forEach((i)->{
					log.info(i);
				});
			}
		});
	}
}
