package com.springboot.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.domain.Board;
import com.springboot.dto.BoardListAllReplyDTO;
import com.springboot.dto.BoardListReplyCountDTO;

public interface BoardSearch {

	Page<Board> search1(Pageable pageable);
	
	Page<Board> searchAll(String[] types , String keyword ,Pageable pageable);
	
	Page<BoardListReplyCountDTO> searchWithReplyCount(String[] type , String keywoard , Pageable pageable);
	
	Page<BoardListAllReplyDTO> searchWithAll(String[] type , String keywoard , Pageable pageable);
		
}
