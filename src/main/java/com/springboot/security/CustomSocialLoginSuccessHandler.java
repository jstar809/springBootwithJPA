package com.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.springboot.dto.MemberSecurityDTO;

public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
	private final static Logger log = LoggerFactory.getLogger(CustomSocialLoginSuccessHandler.class);
	
	private final PasswordEncoder passwordEncoder;
	
	
	public CustomSocialLoginSuccessHandler(PasswordEncoder passwordEncoder) {
		this.passwordEncoder  = passwordEncoder;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("---------------socila login successHandler-----------------");
		log.info(authentication.getPrincipal().toString());
		
		MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
		
		String mpw = memberSecurityDTO.getMpw();
		log.info("what if  {} " ,passwordEncoder.encode( "1111" ));
		
		//소셜 로그인시에 처음 로그인 (db 에저장되지않는) 회원의 비밀번호 초기값은 1111 임 이때 비밀번호 변경 하게 만들게 수정페이지로 이동
		if(memberSecurityDTO.isSocial() && passwordEncoder.matches( "1111" ,mpw ) ) {
			
			log.info("must be change Password");
			response.sendRedirect("/member/modify");
			return;
		}else {
			response.sendRedirect("/board/list");
			return;
		}
		
		
	}

}
