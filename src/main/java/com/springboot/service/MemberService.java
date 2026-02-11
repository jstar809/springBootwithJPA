package com.springboot.service;

import com.springboot.dto.MemberJoinDTO;
import com.springboot.dto.MemberSecurityDTO;

import net.bytebuddy.implementation.bind.annotation.Super;

public interface MemberService {
	
	/*static 멤버서비스 전용 에러*/
	class MidExistException extends Exception{
		public MidExistException(String messgae) {
			super(messgae);
		}
	}
	
	public void register(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
