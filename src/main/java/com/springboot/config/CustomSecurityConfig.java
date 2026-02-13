package com.springboot.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.springboot.controller.BoardController;
import com.springboot.security.CustomSocialLoginSuccessHandler;
import com.springboot.security.CustomUserDeatils;
import com.springboot.security.CustomUserDeatilsService;
import com.springboot.security.handler.Custom403Handler;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

	private final static int REMEMBER_ME_VALID_DAY = 30;
	
    private final DataSource dataSource;
    //private final CustomUserDeatilsService customUserDeatilsService;
    
    
	CustomSecurityConfig(DataSource dataSource  /* , CustomUserDeatilsService customUserDeatilsService*/) {
    	this.dataSource =  dataSource;
    	//this.customUserDeatilsService = customUserDeatilsService;
    }
	
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		log.info("------------security - configure ---------");
		
		//로그인 페이지 on
		http.formLogin().loginPage("/member/login"); 
		
		//csrf 토큰 off
		http.csrf().disable();
		
		//자동 로그인 db 에 토큰을 저장해서 값을 검증
		http.rememberMe()
			.key("123456")
			.tokenRepository(persistentTokenRepository())
			//.userDetailsService(customUserDeatilsService)
			.tokenValiditySeconds(60*80*24*REMEMBER_ME_VALID_DAY);
			
		//403에러 핸들러
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		
		//oauth2 
		http.oauth2Login()
		.loginPage("/member/login")
		.successHandler(authenticationSuccessHandler())
		;
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		
		log.info("------------web - configure ---------");
		
		return (web)->{
			web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		};
		
	}
	
	@Bean
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		
		repo.setDataSource(dataSource);
		
		return repo;
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new Custom403Handler();
	}
	
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomSocialLoginSuccessHandler(passwordEncorder());
	}
}
