package com.springboot.service;

import com.springboot.dto.BoardDTO;
import com.springboot.dto.BoardListReplyCountDTO;
import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;

public interface BoardService {

	Long register(BoardDTO boardDTO);
	
	BoardDTO readOne(Long bno);
	
	void modify(BoardDTO boardDTO);
	
	void remove(Long bno);
	
	PageResponseDTO<BoardDTO> list(PageRequsetDTO pageRequsetDTO);
	
	PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequsetDTO pageRequsetDTO);
}
