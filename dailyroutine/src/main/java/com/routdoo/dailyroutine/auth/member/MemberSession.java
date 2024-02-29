package com.routdoo.dailyroutine.auth.member;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;
import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member
* @fileName      : MemberSession.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.15
* @description   : 회원 세션 정보
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.15        ghgo       최초 생성
 */
@Component
@RequiredArgsConstructor
public class MemberSession {
	
	private final MemberService memberService;
	
	private final JwtProvider jwtProvider;

	private final JwtTokenService jwtTokenService;
	
	public static final String member_key = "_routdoo_member_key";
	
	private Authentication authentication = null;
	
	/**
	 * 회원 세션 등록
	 * @param
	 * @param dto
	 * @throws Exception 
	 */
	public String createMemberSession(MemberDto dto) throws Exception {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPw());		
		
		String token = jwtProvider.createToken(authentication,true);
		String refreshToken = jwtProvider.createRefreshToken(authentication, true);
		
		JwtTokenEntity jwtToken = new JwtTokenEntity(dto.getId(),token,refreshToken);
		jwtTokenService.save(jwtToken);
		return token;
	}
	
	public boolean isAuthenticated() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		return jwtTokenService.find(authentication.getName()) != null;
	}
	
	/**
	 * jwt 인증 정보 redis에서 삭제
	 * @throws Exception 
	 */
	public void clearMemberSession() throws Exception {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		jwtTokenService.delete(authentication.getName());
	}

	/**
	 * 정보 조회
	 * @param
	 * @return
	 * @throws Exception
	 */
	public MemberDto getMemberSession() throws Exception {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberDto dto = new MemberDto();
		System.out.println(authentication.getName());
		dto.setId(authentication.getName());
		return memberService.selectMember(dto);
	}

}
