package com.springboot.domain;

public enum MemberRole {
	USER("ROLE_USER") , 
	ADMIN("ROLE_ADMIN")
	
	;
	
	private String  security_auth;
	
	private MemberRole (String security_auth) {
		this.security_auth = security_auth;
	}

	public String getSecurity_auth() {
		return this.security_auth;
	}
	
}
