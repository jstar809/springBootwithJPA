package com.springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{
	
	@Id
	private String mid;
	
	private String mpw;

	private String email;
	
	private boolean del;
	
	private boolean social;
	
	@Builder.Default
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<MemberRole> roleSet = new HashSet<>();

	
	
	public void changePassword(String mpw) {
		this.mpw = mpw;
	}

	public void changeEmail(String email) {
		this.email = email;
	}
	
	public void changeDel(boolean del) {
		this.del = del;
	}
	
	public void changeSocial(boolean social) {
		this.social = social;
	}
	
	public void addRole (MemberRole memberRole) {
		this.roleSet.add(memberRole);
	}
	
	public void clear () {
		this.roleSet.clear();
	}
	
	
	
}
