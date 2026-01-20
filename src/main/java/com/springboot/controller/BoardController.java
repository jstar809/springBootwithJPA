package com.springboot.controller;

import javax.validation.Valid;
import com.springboot.service.BoardServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.dto.BoardDTO;
import com.springboot.dto.PageRequsetDTO;
import com.springboot.service.BoardService;

@Controller()
@RequestMapping("/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;
	
	
	Logger log = LogManager.getLogger();
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService, BoardServiceImpl boardServiceImpl) {
		this.boardService = boardService;
		this.boardServiceImpl = boardServiceImpl;
	}
	
	
	@GetMapping("/list")
	public String list(@Valid PageRequsetDTO pageRequsetDTO , BindingResult bindingResult , Model model ) {
		
		if( bindingResult.hasErrors() ) {
			model.addAttribute("error", bindingResult.getAllErrors());
			return "/list";
		}
		
		log.info(pageRequsetDTO);
		
		model.addAttribute("responseDTO" ,boardService.list(pageRequsetDTO));
		
		log.info(boardService.list(pageRequsetDTO));
		
		return "/board/list";
		
	}
	
	@GetMapping("/register")
	public String registerGet() {
		
		return "/board/register";
	}
	
	@PostMapping("/register")
	public String registerPost(@Valid BoardDTO boardDto , BindingResult bindingResult , RedirectAttributes redirectAttributes) {
		
		log.info("/register  post ");
		
		if(bindingResult.hasErrors()) {
			log.info("has error");
			redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
			redirectAttributes.addFlashAttribute("boardDto",boardDto);
			return "redirect:/board/register";
		}
		
		Long bno = boardService.register(boardDto);
		redirectAttributes.addFlashAttribute("result",bno);
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read" , "/modify" })
	public void read(Long bno , PageRequsetDTO pageRequsetDTO ,  Model model) {
		
		BoardDTO boardDTO = boardService.readOne(bno);
		
		
		log.info( "boardDto=  ["+boardDTO + "]");
		
		model.addAttribute("dto", boardDTO);
		
		//return "/board/read";
		
	}
	
	@PostMapping("/modify")
	public String modify(
		PageRequsetDTO pageRequsetDTO,
		@Valid BoardDTO boardDTO , 
		BindingResult bindingResult,  
		RedirectAttributes redirectAttributes ,
		Model model
		) {
		
		log.info( "boardDto=  ["+boardDTO + "]");
		
		if(bindingResult.hasErrors()) {
			
			log.info( "errors  ");
			
			redirectAttributes.addAttribute("bno" , boardDTO.getBno());
			redirectAttributes.addFlashAttribute("errors" , bindingResult.getAllErrors());
			return "redirect:/board/modify?"+pageRequsetDTO.getLink();
		}
		
		boardService.modify(boardDTO);
		redirectAttributes.addFlashAttribute("result" , "modify");
		redirectAttributes.addAttribute("bno" , boardDTO.getBno());
		
		return "redirect:/board/read";
	}
	
	@PostMapping("/remove")
	public String remove(Long bno ,RedirectAttributes redirectAttributes ) {
		
		log.info(bno);
		
		boardService.remove(bno);
		redirectAttributes.addFlashAttribute("result" , "delete");
		
		return "redirect:/board/list";
		
	}
	
	
	
	
}
