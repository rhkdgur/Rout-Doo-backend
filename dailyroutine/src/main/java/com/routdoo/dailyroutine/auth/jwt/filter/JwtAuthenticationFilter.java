package com.routdoo.dailyroutine.auth.jwt.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.JwtResultCodeType;
import com.routdoo.dailyroutine.auth.jwt.JwtServiceResult;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtToken;
import com.routdoo.dailyroutine.auth.jwt.repository.JwtTokenRepository;

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
	
	private final JwtTokenRepository jwtTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("Authorization");

		token = token != null ? token.replaceAll("Bearer ", "") : token;
		String requestURI =request.getRequestURI();
		
		JwtServiceResult<Claims> result = jwtProvider.getValidateToken(token);
		System.out.println("auth : "+result.getCodeType().name());
		
		if(StringUtils.hasText(token) && JwtResultCodeType.TOKEN_OK.name().equals(result.getCodeType().name())) {
			String key = result.getElement().getSubject();
			
			JwtToken jwtToken = new JwtToken();
			jwtToken = jwtTokenRepository.findById(key).orElse(null);
			String storedToken = jwtToken == null ? null : jwtToken.getToken();
			
			//로그아웃 정보인지 redis에서 확인 
			if(storedToken != null) {
				try {
					//토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 반
					Authentication authentication = jwtProvider.getAuthentication(storedToken);
					UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) authentication; 
					userAuth.setDetails(request);
					//해당 스프링 시큐리티 유저를 시큐리티컨텍스트에 저장, 즉 디비를거치지 않
					SecurityContextHolder.getContext().setAuthentication(userAuth);
					System.out.println("#### auth : "+userAuth.getName()+","+requestURI+","+userAuth.getAuthorities());
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
		
		filterChain.doFilter(request, response);
	}

}