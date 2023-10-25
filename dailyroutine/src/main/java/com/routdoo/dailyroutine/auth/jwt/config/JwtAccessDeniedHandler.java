package com.routdoo.dailyroutine.auth.jwt.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
  권한 없으 경우 403 전달 
 * @author GAMJA
 *
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Map<String,String> map = new LinkedHashMap<>();
        map.put("statusCode", "403");
        map.put("message", "권한이 없습니다.");
        response.getWriter().write(objectMapper.writeValueAsString(map));
		
		
		//response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

}
