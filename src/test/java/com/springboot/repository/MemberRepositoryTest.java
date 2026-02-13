package com.springboot.repository;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.springboot.domain.Member;
import com.springboot.domain.MemberRole;

@SpringBootTest
public class MemberRepositoryTest {
	
	private static final Logger log = LogManager.getLogger(MemberRepositoryTest.class);
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	
	/*
	 * @Test public void insertMemnerTest() {
	 * 
	 * for(int i=0 ; i<30 ; i++) {
	 * 
	 * Member member = Member.builder() .mid("user"+i)
	 * .mpw(passwordEncoder.encode("1234")) .email("test"+i+"@naver.com") .build();
	 * member.addRole(MemberRole.USER);
	 * 
	 * if(i>20) { member.addRole(MemberRole.ADMIN); }
	 * 
	 * memberRepository.save(member); }
	 * 
	 * }
	 * 
	 */
	
	/*
	 * @Test
	 * 
	 * @Transactional public void getWithRole() {
	 * log.info("------------------ test getWithRole");
	 * 
	 * Member member = memberRepository.getWithRole("user25").orElseThrow();
	 * 
	 * 
	 * log.info(member); log.
	 * info("------------------ ------------------ ------------------ ------------------ ------------------ "
	 * ); log.info(member.getRoleSet()); }
	 */
	
	
	
	
	
	@Test
	@Transactional
	@Commit
	public void testUpdatePassword () {
		try {
			memberRepository.updatePassword(passwordEncoder.encode("1234"), "4746914135@이준선.com");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
}
