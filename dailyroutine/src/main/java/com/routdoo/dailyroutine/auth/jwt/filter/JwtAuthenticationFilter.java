package com.routdoo.dailyroutine.auth.jwt.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.JwtServiceResult;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;
import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 엑세스 토큰 정보를 검증하는 필터 
 * @author GAMJA
 *
 */
//@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtProvider jwtProvider;
	
//	private final JwtTokenRepository jwtTokenRepository;
	private final JwtTokenService jwtTokenService; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtProvider.resolveToken(request);
		String requestURI =request.getRequestURI();
		
		JwtServiceResult<Claims> result = jwtProvider.getValidateToken(token);
		
		if(StringUtils.hasText(token) && JwtResultCodeType.TOKEN_OK.name().equals(result.getCodeType().name())) {
			String key = result.getElement().getSubject();
			
			JwtTokenEntity jwtToken = jwtTokenService.find(key);
			//로그아웃 정보인지 redis에서 확인 
			if(jwtToken != null) {
				String storedToken = jwtToken.getAccessToken();
				if(token.equals(storedToken)) {
					try {
						//토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 반
						Authentication authentication = jwtProvider.getAuthentication(storedToken);
						UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) authentication;
						userAuth.setDetails(request);
						//해당 스프링 시큐리티 유저를 시큐리티컨텍스트에 저장, 즉 디비를거치지 않
						SecurityContextHolder.getContext().setAuthentication(userAuth);
						System.out.println("#### auth : " + userAuth.getName() + "," + requestURI + "," + userAuth.getAuthorities());
					} catch (Exception e) {
						logger.error("### 인증 정보 조회시 이슈 ### " + e.getMessage());
						request.setAttribute("exception", JwtResultCodeType.TOKEN_WRONG.name());
					}
				}else {
					logger.error("### 토큰 인증 정보가 일치하지 않음 ### ");
					request.setAttribute("exception",JwtResultCodeType.TOKEN_WRONG.name());
				}
			}else {
				logger.error("### 인증 만료된 토큰 ### uri : "+requestURI);
				request.setAttribute("exception",JwtResultCodeType.EXPIRED_TOKEN.name());
			}
		}
		
		filterChain.doFilter(request, response);
	}

	public static void setErrorResponse(HttpServletResponse response, HttpStatus errorCode, String message) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorCode.value());
		response.setCharacterEncoding("utf-8");
		Map<String,String> map = new LinkedHashMap<>();
		map.put("statusCode", errorCode.value()+"");
		map.put("message", message);
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(map));
	}

}