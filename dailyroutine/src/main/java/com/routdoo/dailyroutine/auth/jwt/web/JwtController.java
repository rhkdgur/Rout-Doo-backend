package com.routdoo.dailyroutine.auth.jwt.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
public class JwtController {
	
	private final JwtProvider jwtProvider;
	
	private final JwtTokenService jwtTokenService;

	@PostMapping("/api/jwt/token/refresh")
	public Map<String,String> refreshToken(HttpServletRequest request) throws Exception {
		
		Map<String, String> resultMap = new HashMap<>();
		
		String refreshToken = request.getHeader("Authorization");
		
		//jwt 토큰 유효성 인증
		JwtServiceResult<Claims> result = jwtProvider.getValidateToken(refreshToken);
		
		if(StringUtils.hasText(refreshToken) && JwtResultCodeType.TOKEN_OK.name().equals(result.getCodeType().name())) {
			
			//redis에서 토큰이 존재하는지 확인
			JwtTokenEntity jwtToken = jwtTokenService.find(result.getElement().getSubject());
			if(jwtToken != null) {
				//토큰 생성
				UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtProvider.getAuthentication(refreshToken);
				String token = jwtProvider.createToken(authentication, true);
				
				jwtToken = new JwtTokenEntity(jwtToken.getId(),token,refreshToken);
				jwtTokenService.save(jwtToken);
				resultMap.put("accessToken", token);
			}else {
				resultMap.put("accessToken", "empty");
			}
		}else {
			resultMap.put("accessToken", "empty");
		}
		
		return resultMap;
	}
}
