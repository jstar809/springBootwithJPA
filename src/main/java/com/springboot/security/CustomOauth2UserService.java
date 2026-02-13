package com.springboot.security;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springboot.domain.Member;
import com.springboot.domain.MemberRole;
import com.springboot.dto.MemberSecurityDTO;
import com.springboot.repository.MemberRepository;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService{
	private final static Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);
	
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	
	public CustomOauth2UserService(PasswordEncoder passwordEncoder ,MemberRepository memberRepository ) {
		this.passwordEncoder = passwordEncoder;
		this.memberRepository = memberRepository;
		
	}
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		log.info("userRequest     :  [{}]" ,userRequest);
		
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		String clientName = clientRegistration.getClientName();
		
		log.info(clientName);
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> paramMap =  oAuth2User.getAttributes();
		
		log.info("paramMap     :  [{}]" ,paramMap);
		
		String email = null;
		
		switch (clientName) {
		case "kakao":
			Map<String , Object> accountMap = (Map<String , Object> ) paramMap.get("kakao_account");
			
			//예전에는 이메일도 정보 받을수 있엇는데... 정책이 변경된건지 사업자 등록을해야지 개인정보를 모두 가져올수 있음...
			//email = (String) accountMap.get("email");
			
			//이메일을 가져올수 없기에 가져올수 있는정보 nickname _ 카카오 고유 id로 임의이 이메일을 생성 하자
			Map<String , Object> profileMap = (Map<String , Object> ) accountMap.get("profile");
			String nickName = (String) profileMap.get("nickname");
			Long id = (Long) paramMap.get("id");
			email = id +"@" + nickName +".com";
			
			log.info("kakao email {}" , email);
			
			break;

		default:
			break;
		} 
		
		return generateDTO(email , paramMap);
	}
	
	
	private MemberSecurityDTO generateDTO(String email , Map<String , Object> params) {
		
		Optional<Member> result = memberRepository.findByEmail(email);

		//sns 로그인시 db 이메일 정보가 없다면 db 에저장   있다면 그냥 dto 만전달 
		// ci 나 개인을 식별할수 잇는값으로 해야 하는데 .. 받아올수가 없음...
		if(result.isEmpty()) {
			Member member = Member.builder()
					.mid(email)
					.email(email)
					.mpw(passwordEncoder.encode( "1111") )
					.social(true)
					.build();
			member.addRole(MemberRole.USER);
			
			memberRepository.save(member);	
			
			MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
					email, "1111", email
					, false, true
					, Arrays.asList(new SimpleGrantedAuthority(MemberRole.USER.getSecurity_auth()))
					);
					
			
			return memberSecurityDTO;
			
		}else {
			Member member = result.get();
			
			MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
					member.getMid(), member.getMpw(), member.getEmail()
					, member.isDel(), member.isSocial()
					, member.getRoleSet().stream()
						.map((role)-> new SimpleGrantedAuthority(role.getSecurity_auth()))
						.collect(Collectors.toList())
					);
					
			
			return memberSecurityDTO;
		}
		
	}
}
