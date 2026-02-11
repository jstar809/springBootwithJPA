package com.springboot.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.domain.Member;
import com.springboot.dto.MemberSecurityDTO;
import com.springboot.repository.MemberRepository;

@Service
public class CustomUserDeatilsService implements UserDetailsService{
	
	private final static Logger log = LoggerFactory.getLogger(CustomUserDeatilsService.class);
	
	private final PasswordEncoder passwordEncoder;
	
	private final MemberRepository memberRepository;
	
	public CustomUserDeatilsService(PasswordEncoder passwordEncoder ,MemberRepository memberRepository) {
		this.passwordEncoder = passwordEncoder;
		this.memberRepository = memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("userDetailSerivece--------------------userName : {}" , username);
		
		Optional<Member> result  = memberRepository.getWithRole(username);
		
		if(result.isEmpty()) {
			throw new UsernameNotFoundException("userNotFound .. " + username);
		}
		
		Member member = result.get();
		
		String mid = member.getMid();
		String mpw = member.getMpw();
		String email = member.getEmail();
		boolean del = member.isDel();
		boolean social = member.isSocial();
		List <GrantedAuthority> authorities = member.getRoleSet()
								.stream().map((auth)->{
									return new SimpleGrantedAuthority(auth.getSecurity_auth());
								}).collect(Collectors.toList());

		log.info(new MemberSecurityDTO(mid , mpw , email , del ,social  ,authorities).toString());
		
		
		return new MemberSecurityDTO(mid , mpw , email , del ,social  ,authorities);
	}

}
