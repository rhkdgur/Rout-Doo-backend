package com.routdoo.dailyroutine.auth.jwt.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter.setErrorResponse;

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
        setErrorResponse(response, HttpStatus.FORBIDDEN,"권한이 없습니다.");
	}

}
