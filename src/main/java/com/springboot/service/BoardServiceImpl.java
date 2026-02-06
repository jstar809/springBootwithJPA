package com.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.domain.Board;
import com.springboot.dto.BoardDTO;
import com.springboot.dto.BoardListAllReplyDTO;
import com.springboot.dto.BoardListReplyCountDTO;
import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;
import com.springboot.repository.BoardRepository;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	
	private final Logger log = LogManager.getLogger(BoardServiceImpl.class);
	
	private final ModelMapper modelMapper;
	private final BoardRepository boardRepository;
	
	public BoardServiceImpl(ModelMapper modelMapper ,BoardRepository boardRepository) {
		this.modelMapper = modelMapper;
		this.boardRepository = boardRepository;
		
	}
	
	@Override
	public Long register(BoardDTO boardDTO) {
		log.info("register : BoardDTO " + boardDTO);
		
		Board boardEntity = dtoToEntity(boardDTO);
		
		Board boardResult = boardRepository.save(boardEntity);
		
		
		return boardResult.getBno();
	}

	@Override
	public BoardDTO readOne(Long bno) {

		Optional<Board> result = boardRepository.findByIdWithImage(bno);
		Board board = result.orElseThrow();
		
		log.info(board);
		
		return entityToDTO(board);
	}

	@Override
	public void modify(BoardDTO boardDTO) {
		Optional<Board> result = boardRepository.findById(boardDTO.getBno());
		Board board = result.orElseThrow();
		
		log.info("board      " + board );
		
		board.change(boardDTO.getTitle() , boardDTO.getContent());
		board.clearImages();
		
		if(boardDTO.getFileNames() != null) {
			log.info("boardDTO.getFileNames()      " + boardDTO.getFileNames() );
			boardDTO.getFileNames().forEach((fn)->{
				String[] arr = fn.split("_");
				board.addImage(arr[0], arr[1]);
			});
		}
		
		
		boardRepository.save(board);
		
	}

	@Override
	public void remove(Long bno) {
		
		boardRepository.deleteById(bno);
	}

	@Override
	public PageResponseDTO<BoardDTO> list(PageRequsetDTO pageRequsetDTO) {
		
		String keyword = pageRequsetDTO.getKeyword();
		String[] types = pageRequsetDTO.getTypes();
		Pageable pageable = pageRequsetDTO.getPageable("bno");
		
		Page<Board> result =  boardRepository.searchAll(types, keyword, pageable);

		List<BoardDTO> boardDtoList = result.getContent().stream().map( (board) ->
			modelMapper.map(board, BoardDTO.class)
		).collect(Collectors.toList());
		
		PageResponseDTO<BoardDTO> boardDTO =  
				PageResponseDTO.<BoardDTO>withAll()
				.pageRequsetDTO(pageRequsetDTO)
				.dtoList(boardDtoList)
				.total((int)result.getTotalElements())
				.build();
		
		return boardDTO;
	}

	@Override
	public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequsetDTO pageRequsetDTO) {
		String[] type = pageRequsetDTO.getTypes();
		String keyword = pageRequsetDTO.getKeyword();
		Pageable pageable = pageRequsetDTO.getPageable("bno");
				
		
		
		Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(type, keyword, pageable);
		List<BoardListReplyCountDTO> boardDTO = result.getContent();
        int total = (int)result.getTotalElements();
		
        PageResponseDTO pageResponseDTO = PageResponseDTO.<BoardListReplyCountDTO>withAll()
			.dtoList(boardDTO)
			.pageRequsetDTO(pageRequsetDTO)
			.total(total)
			.build();
		
		return pageResponseDTO;
	}

	@Override
	public PageResponseDTO<BoardListAllReplyDTO> listWithAll(PageRequsetDTO pageRequsetDTO) {
		
		Page<BoardListAllReplyDTO> result = boardRepository.searchWithAll(
									pageRequsetDTO.getTypes(),
									pageRequsetDTO.getKeyword() , 
									pageRequsetDTO.getPageable("bno")
								);
		
		
		PageResponseDTO<BoardListAllReplyDTO> responseDTO =  
				PageResponseDTO.<BoardListAllReplyDTO>withAll()
								.pageRequsetDTO(pageRequsetDTO)	
								.dtoList(result.getContent())
								.total((int) result.getTotalElements())
								.build();
		
		return responseDTO;
	}

}
