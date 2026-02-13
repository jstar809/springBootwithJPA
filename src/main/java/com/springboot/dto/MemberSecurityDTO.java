package com.springboot.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class MemberSecurityDTO extends User implements OAuth2User{
	
	private String mid;
	
	private String mpw;
	
	private String email;
	
	private boolean del;
	
	private boolean social;
	
	private Map<String , Object> socialProps;

	public MemberSecurityDTO(String username, String password, 
			String email ,boolean del , boolean social
			,Collection<? extends GrantedAuthority> authorities ) {
		
		super(username, password, authorities);

		this.mpw =password;
		this.mid=username;
		this.email=email;
		this.del=del;
		this.social=social;
		
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.socialProps;
	}

	@Override
	public String getName() {
		return this.mid;
	}


	
}
