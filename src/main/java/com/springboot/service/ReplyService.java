package com.springboot.service;

import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;
import com.springboot.dto.ReplyDTO;

public interface ReplyService {

	Long register(ReplyDTO replyDTO);
	
	ReplyDTO read(long rno);
	
	void modify(ReplyDTO replyDTO);
	
	void remove(long rno);
	
	PageResponseDTO<ReplyDTO> getListOfBoard(long bno , PageRequsetDTO pageRequsetDTO);
}
