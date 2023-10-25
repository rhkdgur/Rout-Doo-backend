package com.routdoo.dailyroutine.auth.jwt.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 인증 실패시 처리 부분 (401) 전달 
 * @author GAMJA
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
	        ObjectMapper objectMapper = new ObjectMapper();

	        response.setStatus(HttpStatus.FORBIDDEN.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.setCharacterEncoding("utf-8");
	        Map<String,String> map = new LinkedHashMap<>();
	        map.put("statusCode", "401");
	        map.put("message", "인증정보가 없습니다.");
	        response.getWriter().write(objectMapper.writeValueAsString(map));
		
	        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
 
}
