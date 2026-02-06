package com.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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

	@Value("${project.upload.path}")
	private String UPLOAD_PATH;
	
	Logger log = LogManager.getLogger();
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	
	@GetMapping("/list")
	public String list(@Valid PageRequsetDTO pageRequsetDTO , BindingResult bindingResult , Model model ) {
		
		if( bindingResult.hasErrors() ) {
			model.addAttribute("error", bindingResult.getAllErrors());
			return "/list";
		}
		
		log.info(pageRequsetDTO);
		
		model.addAttribute("responseDTO" ,boardService.listWithAll(pageRequsetDTO));
		
		log.info(boardService.listWithAll(pageRequsetDTO));
		
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
	public String remove(BoardDTO boardDTO ,RedirectAttributes redirectAttributes ) {
		
		log.info(boardDTO.getBno());
		
		boardService.remove(boardDTO.getBno());
		
		List<String> fileNames = boardDTO.getFileNames();
		
		if (fileNames.size() > 0 && fileNames != null) {
			removeFile(fileNames);
		}
		
		redirectAttributes.addFlashAttribute("result" , "delete");
		
		return "redirect:/board/list";
		
	}
	
	private void removeFile(List<String> fileNames ) {
		for(String fileName : fileNames) {
			Resource resource = new FileSystemResource(UPLOAD_PATH+ File.separator + fileName);
			
			String rsName = resource.getFilename();
			
			try {
				resource.getFile().delete();
				String contentType = Files.probeContentType(resource.getFile().toPath());
				
				if(contentType.startsWith("image")) {
					File thumFile = new File(UPLOAD_PATH + File.separator + "s_" +  fileName);
					thumFile.delete();
				}
				
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	
	
	
}
