package com.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.dto.MemberJoinDTO;
import com.springboot.service.MemberService;
import com.springboot.service.MemberService.MidExistException;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private final MemberService memberService;
	
	public MemberController(MemberService memberService ) {
		this.memberService = memberService;
	}
	
	
	@GetMapping("/login")
	public String login(String error , String logout) {
		
		log.info("getting Login");
		
		log.info("logOut : {}" ,logout);
		log.info("error : {} ",error);
		
		if(logout != null) {
			log.info("user LogOut");
		}
		
		return "/member/login";
	}
	
	@GetMapping("/join")
	public String getJoin() {
		log.info("get join...");
		return "/member/join";
	}
	
	
	@PostMapping("/join")
	public String postJoin(MemberJoinDTO memberJoinDTO , RedirectAttributes redirectAttributes) {
		
		log.info("post join... memberJoinDTO [ {} ]" ,memberJoinDTO);
		
		try {
			memberService.register(memberJoinDTO);
		} catch (MidExistException e) {
			log.error(e.getMessage());
			
			redirectAttributes.addFlashAttribute( "error" ,e.getMessage());
			return "redirect:/member/join";
		}
		
		redirectAttributes.addFlashAttribute("result" ,"suceess");
		
		return "redirect:/board/list";
	}
	
}
