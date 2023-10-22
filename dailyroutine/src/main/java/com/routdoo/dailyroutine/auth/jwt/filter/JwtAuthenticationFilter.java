package com.routdoo.dailyroutine.auth.jwt.filter;

import java.io.IOException;

import org.hibernate.query.internal.ResultMementoEntityJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.JwtServiceResult;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 엑세스 토큰 정보를 검증하는 필터 
 * @author GAMJA
 *
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtProvider jwtProvider;
	
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String token = req.getHeader("Authorization");
		String requestURI =req.getRequestURI();
		
		JwtServiceResult<Claims> result = jwtProvider.getValidateToken(token);
		if(token != null && JwtResultCodeType.TOKEN_OK.name().equals(result.getCodeType().name())) {
			String key = "jwtToken:"+token;
			String storedToken = redisTemplate.opsForValue().get(key);
			
			//로그아웃 정보인지 redis에서 확인 
			if(redisTemplate.hasKey(key) && storedToken != null) {
				//토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 반
				Authentication authentication;
				try {
					authentication = jwtProvider.getAuthentication(token);
					//해당 스프링 시큐리티 유저를 시큐리티컨텍스트에 저장, 즉 디비를거치지 않
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} catch (Exception e) {
					logger.error("### 인증 정보 조회시 이슈 발생 ### {}",e.getMessage());
				}
				logger.debug("인증 정보를 저장했습니다. uri :{}",requestURI);
			}else {
				logger.debug("인증 만료된 토큰입니다. uri :{}",requestURI);
			}
		}else {
			logger.debug("유효한 토큰이 없습니다. uri : {}",requestURI);
		}
		
		chain.doFilter(request, response);
	}

}