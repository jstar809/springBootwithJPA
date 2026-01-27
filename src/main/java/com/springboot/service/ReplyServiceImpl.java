package com.springboot.service;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.domain.Reply;
import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;
import com.springboot.dto.ReplyDTO;
import com.springboot.repository.ReplyRepository;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
	private static final  Logger log = LogManager.getLogger();
	
	private final ReplyRepository replyRepository;
	private final ModelMapper modelMapper;
	
	public ReplyServiceImpl(ReplyRepository replyRepository , ModelMapper modelMapper) {
		this.replyRepository =  replyRepository;
		this.modelMapper =  modelMapper;
	}
	
	@Override
	public Long register(ReplyDTO replyDTO) {
		
		Reply reply = modelMapper.map(replyDTO, Reply.class);
		
		log.info(reply);
		
		long result = replyRepository.save(reply).getRno();
		
		
		return result;
	}

	@Override
	public ReplyDTO read(long rno) {
		
		log.info(rno);
		
		Reply reply =  replyRepository.findById(rno).orElseThrow();
		
		return modelMapper.map(reply, ReplyDTO.class);
	}

	@Override
	public void modify(ReplyDTO replyDTO) {
		
		log.info(replyDTO);
		
		Reply reply =  replyRepository.findById(replyDTO.getRno()).orElseThrow();
		reply.changeText(replyDTO.getReplyText());
		
		replyRepository.save(reply);
	}

	
	@Override
	public void remove(long rno) {
		replyRepository.deleteById(rno);
	}

	@Override
	public PageResponseDTO<ReplyDTO> getListOfBoard(long bno ,PageRequsetDTO pageRequsetDTO) {
		
		// 커스텀 패이징
		Pageable pageable =  PageRequest.of(
						pageRequsetDTO.getPage()<=0 ? 0: pageRequsetDTO.getPage()-1 
								,pageRequsetDTO.getSize() 
								,Sort.by("rno").ascending());
		
		Page<Reply> result =  replyRepository.listOfBoard(bno, pageable);
		List<Reply> listReply = result.getContent();
		
		List<ReplyDTO> listReplyDTO =  listReply.stream().map((reply)->{
			return modelMapper.map(reply, ReplyDTO.class);
		}).collect(Collectors.toList());
		
		
		return PageResponseDTO.<ReplyDTO>withAll()
				.pageRequsetDTO(pageRequsetDTO)
				.dtoList(listReplyDTO)
				.total((int)result.getTotalElements())
				.build();
	}

}
