package com.springboot.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.domain.Member;
import com.springboot.domain.MemberRole;
import com.springboot.dto.MemberJoinDTO;
import com.springboot.repository.MemberRepository;

@Service
@Transactional
public class MemberSeriviceImpl implements MemberService {
	private final static Logger log = LoggerFactory.getLogger(MemberSeriviceImpl.class);
	
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public MemberSeriviceImpl(MemberRepository memberRepository ,ModelMapper modelMapper ,PasswordEncoder passwordEncoder ) {
		this.memberRepository = memberRepository;
		this.modelMapper =modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void register(MemberJoinDTO memberJoinDTO) throws MidExistException {
		log.info(" memberJoinDTO  [ {} ]" , memberJoinDTO.toString());
		
		Optional<Member> result = memberRepository.findById(memberJoinDTO.getMid());
		
		if(!result.isEmpty()) {
			throw new MidExistException("동일한 id 를가진 계정이 존재합니다");
		}
		
		memberJoinDTO.setMpw(passwordEncoder.encode(memberJoinDTO.getMpw()));
		
		Member member = modelMapper.map(memberJoinDTO, Member.class);
		member.addRole(MemberRole.USER);
		
		log.info(" member  [ {} ]" , member.toString());
		
		Member savedMember =  memberRepository.save(member);
		log.info(" savedMember  [ {} ]" , savedMember.toString());
		
	}

	
}
