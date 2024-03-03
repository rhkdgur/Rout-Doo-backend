package com.routdoo.dailyroutine.auth.jwt.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter.setErrorResponse;

/**
 * 인증 실패시 처리 부분 (401) 전달 
 * @author GAMJA
 *
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String exception = (String) request.getAttribute("exception");

		// 1. 토큰 없음 2. 시그니처 불일치
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("토큰이 존재하지 않거나 Bearer로 시작하지 않는 경우");
			setErrorResponse(response, HttpStatus.UNAUTHORIZED,"잘못된 토큰입니다");
		} else if(exception != null){
			// 3. 토큰 만료
			setErrorResponse(response,HttpStatus.UNAUTHORIZED,JwtResultCodeType.valueOf(exception).getDisplay());
		}

	}
 
}
