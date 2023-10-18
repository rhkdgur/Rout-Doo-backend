package com.routdoo.dailyroutine.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.routdoo.dailyroutine.auth.member.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt
* @fileName      : JwtProvider.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.19
* @description   : Jwt 토큰 util
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.19        ghgo       최초 생성
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {
	
	protected final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	protected static final String AUTHORITIES_KEY = "routdoo-auth";
	
	private String secret;
	
	private long tokenValidityInMilliseconds;

	private Key key;
	
	private final MemberService memberService;
	
	@PostConstruct
	protected void init() {
		JwtProperties jwtProperties = new JwtProperties();
		this.secret = jwtProperties.getSecretKey();
		this.tokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInSeconds() * 1000;
		//시크릿 값을 decode해서 키 변수에 할당
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	
	/**
	 * 토큰 생성
	 * @param authentication
	 * @return
	 */
	public String createToken(Authentication authentication) {
		
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);
		
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key,SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
	}
	
	// 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
    	return null;
    }
	
	
	// 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
	
}
