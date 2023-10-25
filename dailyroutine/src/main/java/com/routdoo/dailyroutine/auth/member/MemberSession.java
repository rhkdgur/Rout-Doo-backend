package com.routdoo.dailyroutine.auth.member;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtToken;
import com.routdoo.dailyroutine.auth.jwt.repository.JwtTokenRepository;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;

import io.jsonwebtoken.Claims;
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

	private final JwtTokenRepository jwtTokenRepository;
	
	public static final String member_key = "_routdoo_member_key";
	
	/**
	 * 회원 세션 등록
	 * @param session
	 * @param dto
	 */
	public String createMemberSession(MemberDto dto) {
//		Map<String,Object> map = new HashMap<>();
//		map.put("id", dto.getId());
//		map.put("key", member_key);
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPw());		
		
		String token = jwtProvider.createToken(authentication,true);
		LocalDateTime nowTime = LocalDateTime.now();
		JwtToken jwtToken = new JwtToken();
		jwtToken.addJwtToken(token,dto.getId(), nowTime);
		jwtTokenRepository.save(jwtToken);
		return token;
	}
	
	/**
	 * 회원 세션 유지 여부
	 * @param session
	 * @return
	 */
	public boolean isSessionKeepup(String token) {
		token = token != null ? token.replaceAll("Bearer ", "") : token;
		return jwtProvider.getValidateToken(token).getCodeType().name()
				.equals(JwtResultCodeType.TOKEN_OK.name()) ? true : false;
	}
	
	public String refreshMemberSession(String token) {
		token = token != null ? token.replaceAll("Bearer ", "") : token;
		if(isSessionKeepup(token)) {
			Claims vo = (Claims) jwtProvider.getValidateToken(token);
			String refreshToken = jwtProvider.createRefreshToken(token);
			JwtToken jwtToken = new JwtToken();
			jwtToken = jwtTokenRepository.findById(vo.getSubject()).orElse(null);
			if(jwtToken != null) {	
				jwtTokenRepository.delete(jwtToken);
				LocalDateTime nowTime = LocalDateTime.now();
				jwtToken.addJwtToken(refreshToken, jwtToken.getId(), nowTime);
			}
			jwtTokenRepository.save(jwtToken);
			return token;
		}
		return null;
	}
	
	/**
	 * 회원 정보
	 * @param session
	 * @return
	 */
	public MemberDto getMemberSession(String token) {
		token = token != null ? token.replaceAll("Bearer ", "") : token;
		Claims vo =  jwtProvider.getValidateToken(token).getElement();
		JwtToken jwtToken = new JwtToken();
		jwtToken = jwtTokenRepository.findById(vo.getSubject()).orElse(null);
		return memberService.selectMemberSession(jwtToken.getId());
	}
	
	/**
	 * 회원 세션 삭제
	 * @param session
	 */
	public boolean clearMemberSession(String token) {
		token = token != null ? token.replaceAll("Bearer ", "") : token;
		if(isSessionKeepup(token)) {
			Claims vo =  jwtProvider.getValidateToken(token).getElement();
			if(jwtTokenRepository.findById(vo.getSubject()).orElse(null) != null) {
				jwtTokenRepository.deleteById(vo.getSubject());
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

}
