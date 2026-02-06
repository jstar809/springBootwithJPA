package com.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.domain.Board;
import com.springboot.dto.BoardDTO;
import com.springboot.dto.BoardListAllReplyDTO;
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
	
	PageResponseDTO<BoardListAllReplyDTO> listWithAll(PageRequsetDTO pageRequsetDTO);
	
	
	default Board dtoToEntity(BoardDTO boardDTO) {
		
		Board board = Board.builder()
						.bno(boardDTO.getBno())
						.title(boardDTO.getTitle())
						.content(boardDTO.getContent())
						.writer(boardDTO.getWriter())
						.build();
		
		if(boardDTO.getFileNames() != null) {
			boardDTO.getFileNames().forEach((fn)->{
				String[] arr = fn.split("_");
				board.addImage(arr[0], arr[1]);
			});
		}
		
		
		return board;
	}
	
	default BoardDTO entityToDTO(Board board) {
		
		 List<String> fileNmaes = board.getImageSet().stream().sorted()
			.map((image)->{
				return image.getUuid() +"_"+image.getFileName() ;
		}).collect(Collectors.toList());
		
		BoardDTO boardDTO = BoardDTO.builder()
						.bno(board.getBno())
						.title(board.getTitle())
						.content(board.getContent())
						.writer(board.getWriter())
						.fileNames(fileNmaes)
						.regDate(board.getRegDate())
						.modDate(board.getModDate())
						.build();
		
		
		
		return boardDTO;
	}
}
