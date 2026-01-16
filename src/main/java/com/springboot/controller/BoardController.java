package com.springboot.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.dto.PageRequsetDTO;
import com.springboot.service.BoardService;

@Controller()
@RequestMapping("/board")
public class BoardController {
	
	
	Logger log = LogManager.getLogger();
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	
	@GetMapping("/list")
	public String list(@Valid PageRequsetDTO pageRequsetDTO , BindingResult bindingResult , Model model) {
		
		if( bindingResult.hasErrors() ) {
			model.addAttribute("error", bindingResult.getAllErrors());
			return "/list";
		}
		
		log.info(pageRequsetDTO);
		
		model.addAttribute("responseDTO" ,boardService.list(pageRequsetDTO));
		
		log.info(boardService.list(pageRequsetDTO));
		
		return "/board/list";
		
	}
	
	
	
	
	
}
