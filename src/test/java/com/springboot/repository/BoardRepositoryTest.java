package com.springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.springboot.dto.BoardListAllReplyDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {
	
	@Autowired
	BoardRepository boardRepository;
	
	/*
	 * @Test public void insertTest() { IntStream.rangeClosed(1, 10).forEach( i-> {
	 * Board board = Board.builder() .title("title"+i) .content("constent"+ i)
	 * .writer("user"+i%10) .build();
	 * 
	 * Board res = boardRepository.save(board);
	 * 
	 * log.info(res); });
	 * 
	 * 
	 * }
	 * 
	 * @Test public void selectTest() {
	 * 
	 * long bno = 1L;
	 * 
	 * Optional<Board> result = boardRepository.findById(bno);
	 * 
	 * Board board = result.orElseThrow();
	 * 
	 * log.info(board); }
	 */
	
	/*
	 * @Test public void updateTest() {
	 * 
	 * long bno = 2L;
	 * 
	 * Optional<Board> result = boardRepository.findById(bno);
	 * 
	 * Board board = result.orElseThrow();
	 * 
	 * board.change("수정", "내용");
	 * 
	 * boardRepository.save(board);
	 * 
	 * 
	 * }
	 */
	
	/*
	 * @Test public void deleteTest() {
	 * 
	 * long bno = 1L;
	 * 
	 * boardRepository.deleteById(bno);
	 * 
	 * }
	 */
	/*
	 * @Test public void pageTest() {
	 * 
	 * Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
	 * 
	 * Page<Board> result =boardRepository.findAll(pageable); Page<Board> result2
	 * =boardRepository.findByTitle("title" , pageable);
	 * 
	 * log.info(result.getTotalElements()); log.info(result.getTotalPages());
	 * log.info(result.getSize()); log.info(result.getContent());
	 * log.info(result.getNumber());
	 * 
	 * }
	 */
	
	
	/*
	 * @Test public void queryDslSelect1() {
	 * boardRepository.search1(PageRequest.of(0, 10, Sort.by("bno").descending()));
	 * 
	 * 
	 * }
	 * 
	 * @Test public void queryDslSelect2() { boardRepository.searchAll( new
	 * String[]{"t" ,"c"} , "d" ,PageRequest.of(0, 10,
	 * Sort.by("bno").descending()));
	 * 
	 * 
	 * }
	 */
	
	
	/*
	 * @Test public void queryDslSelect3() {
	 * 
	 * 
	 * 
	 * boardRepository.searchWithReplyCount( new String[] {"t" , "c"}, "title" ,
	 * PageRequest.of(0, 10,Sort.by("bno").descending()));
	 * 
	 * }
	 */
	 
	
	
	/*
	 * @Test public void testInsertBoardImage() {
	 * 
	 * Board board = Board.builder() .title("image test") .content("content dd")
	 * .writer("tester") .build();
	 * 
	 * for(int i =0 ; i < 10 ; i++) {
	 * 
	 * board.addImage(UUID.randomUUID().toString(), "file_"+i +"name"); }
	 * 
	 * 
	 * boardRepository.save(board);
	 * 
	 * }
	 */
	
	/*
	 * @Test public void testReadOneBoardImage() { Board board = null;
	 * 
	 * //1 세션 닫히네? .. board = boardRepository.findById(39L).orElseThrow();
	 * 
	 * 
	 * //2 EntityGraph 사용 board=
	 * boardRepository.findByIdWithImage(39L).orElseThrow();
	 * 
	 * 
	 * log.info(board); log.info("-------------------------");
	 * log.info(board.getImageSet()); }
	 */
	
	/*
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @Commit public void testModifyBoardImage() {
	 * 
	 * Board board = boardRepository.findByIdWithImage(41L).orElseThrow();
	 * 
	 * board.change("updatedd", board.getContent());
	 * 
	 * 
	 * board.clearImages();
	 * 
	 * for(int i = 0 ; i<= 3 ; i++) { board.addImage(UUID.randomUUID().toString(),
	 * "fileNameTest"+i); }
	 * 
	 * boardRepository.save(board);
	 * 
	 * }
	 */
	
	/*
	 * @Test public void testDummyDataAll() {
	 * 
	 * final int BOARD_TEST_COUNT = 100; final int REPLY_TEST_COUNT = 3; final int
	 * BOARDIMAGE_TEST_COUNT = 3;
	 * 
	 * 
	 * 
	 * for (int i = 0 ; i < BOARD_TEST_COUNT ; i++ ) {
	 * 
	 * Board board = Board.builder() .title("title" + i) .content("content"+i)
	 * .writer("writer" + i) .build();
	 * 
	 * if(i%5 != 0) {
	 * 
	 * for(int j = 0 ; j < BOARDIMAGE_TEST_COUNT ; j++ ) {
	 * 
	 * board.addImage(UUID.randomUUID().toString(), i +"-"+ j +"filename"); } }
	 * 
	 * 
	 * boardRepository.save(board);
	 * 
	 * for(int k = 0 ; k < REPLY_TEST_COUNT ; k++ ) {
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */
	
	
	@Test
	@Transactional
	public void searchWithAll() {
		
		Pageable pageAble = PageRequest.of(0 ,10 , Sort.by("bno").descending());
		
		Page<BoardListAllReplyDTO> result = boardRepository.searchWithAll(new String[] {"T" , "W" , "C"}, "content1",pageAble );
		
		List<BoardListAllReplyDTO> resultList =  result.getContent();
		
		log.info(resultList);
		
		
		
		resultList.forEach((i)->{
			log.info(i.getBoardImage());
		});
		
	
	}
	
}
