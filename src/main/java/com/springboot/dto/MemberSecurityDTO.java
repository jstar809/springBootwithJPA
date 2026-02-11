package com.springboot.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class MemberSecurityDTO extends User{
	
	private String mid;
	
	private String mpw;
	
	private String email;
	
	private boolean del;
	
	private boolean social;

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


	
}
