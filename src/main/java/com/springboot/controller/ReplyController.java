package com.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.PageRequsetDTO;
import com.springboot.dto.PageResponseDTO;
import com.springboot.dto.ReplyDTO;
import com.springboot.repository.ReplyRepository;
import com.springboot.service.ReplyService;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyRepository replyRepository;
	
	Logger log =LogManager.getLogger();
	
	private final ReplyService replyService;
	
	public ReplyController(ReplyService replyService, ReplyRepository replyRepository) {
		this.replyService  = replyService;
		this.replyRepository = replyRepository;
	}
	
	
	@ApiOperation(value = "reply Post" , notes = "POSt 방식으로 댓글 등록"  )
	@PostMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Map<String , Object>> register(@RequestBody @Valid ReplyDTO replyDTO , BindingResult bindingResult ) throws BindException{
		log.info(replyDTO);
		
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		
		Map<String ,Object> result = new HashMap<>();
		result.put("rno" ,replyService.register(replyDTO));
		
		return ResponseEntity.ok(result);
	}
	
	@ApiOperation(value = "reply of Board" , notes = "GET 방식으로 특정 게시글의 댓글 목록"  )
	@GetMapping("/list/{bno}")
	public PageResponseDTO<ReplyDTO> getList(@PathVariable Long bno , PageRequsetDTO pageRequsetDTO){
		return replyService.getListOfBoard(bno, pageRequsetDTO);
		
		
	}
	
	@ApiOperation(value = "Read reply" , notes = "GET 방식으로 특정 게시글의 댓글 조회"  )
	@GetMapping("/{rno}")
	public ReplyDTO getReplyDTO(@PathVariable Long rno , PageRequsetDTO pageRequsetDTO ) {
		
		return replyService.read(rno);
	}
	
	@ApiOperation(value = "delete reply" , notes = "delete 방식으로 특정 게시글의 댓글 삭제"  )
	@DeleteMapping("/{rno}")
	public Map<String, Long> deleteReply(@PathVariable long rno ) {
		replyService.remove(rno);
		
		Map<String , Long> resultMap = new HashMap<>();
		
		resultMap.put("rno" , rno );
		
		return resultMap;
		
	}
	
	@ApiOperation(value = "update reply" , notes = "put 방식으로 특정 게시글의 댓글 수정"  )
	@PutMapping(value =  "/{rno}" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String ,Long> modifyReply(@PathVariable String id, @RequestBody ReplyDTO replyDTO) {
		
		replyService.modify(replyDTO);
		
		Map<String ,Long> resultMap = new HashMap<>();
		resultMap .put("rno", replyDTO.getRno());
		return resultMap;
	}
}
