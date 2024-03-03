package com.routdoo.dailyroutine.auth.jwt.web;

import java.util.HashMap;
import java.util.Map;

import com.routdoo.dailyroutine.auth.admin.service.AdminService;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.JwtServiceResult;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;
import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.web
* @fileName      : JwtController.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.30
* @description   : jwt 토큰 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.30        ghgo       최초 생성
 */
@Tag(name = "토큰 컨트롤러")
@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
public class JwtController {
	
	private final JwtProvider jwtProvider;
	
	private final JwtTokenService jwtTokenService;

	private final MemberService memberService;

	private final AdminService adminService;

	@Operation(summary = "사용자 토큰 재생성")
	@PostMapping("/api/jwt/token/refresh")
	public ResponseEntity<Map<String,String>> refreshToken(HttpServletRequest request) throws Exception {
		
		Map<String, String> resultMap = new HashMap<>();
		
		String token = jwtProvider.resolveToken(request);
		
		//jwt 토큰 유효성 인증
		JwtServiceResult<Claims> result = jwtProvider.getValidateToken(token);

		if(!result.getCodeType().name().equals(JwtResultCodeType.EXPIRED_TOKEN.name()) && !result.getCodeType().name().equals(JwtResultCodeType.TOKEN_OK.name())){
			resultMap.put("msg", "지원되지 않는 JWT 토큰입니다.");
			return new ResponseEntity<>(resultMap,HttpStatus.UNAUTHORIZED);
		}
			
		//redis에서 토큰이 존재하는지 확인
		JwtTokenEntity jwtToken = jwtTokenService.find(result.getElement().getSubject());
		if(jwtToken != null) {

			MemberDto dto = memberService.selectMemberSession(result.getElement().getSubject());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPw());

			//토큰 생성
			token = jwtProvider.createToken(authentication, result.getElement().getIssuer().equals("user"));
			String refreshToken = jwtProvider.createRefreshToken(result.getElement().getIssuer().equals("user"));

			jwtToken = new JwtTokenEntity(jwtToken.getId(),token,refreshToken);
			jwtTokenService.save(jwtToken);

			resultMap.put("accessToken", token);
			resultMap.put("msg", "토큰 재생성 완료");
		}else {
			resultMap.put("msg", "유효하지 않은 토큰입니다.");
			return new ResponseEntity<>(resultMap,HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(resultMap,HttpStatus.OK);
	}
}
