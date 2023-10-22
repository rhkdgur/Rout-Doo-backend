package com.routdoo.dailyroutine.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.routdoo.dailyroutine.auth.admin.service.AdminService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
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
	
	private final JwtProperties jwtProperties;
	
	private final AdminService adminService;
	
	protected final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	protected final String AUTHORITIES_KEY = "routdoo-auth";
	
	private String secret;
	
	private long tokenValidityInMilliseconds;

	private Key key;
	
	@PostConstruct
	protected void init() {
		this.secret = jwtProperties.getSecretkey();
		this.tokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInSeconds() * 1000;
		//시크릿 값을 decode해서 키 변수에 할당
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	
	/**
	 * 권한 토큰 생성
	 * @param authentication
	 * @return
	 */
	public String createAdminToken(Authentication authentication) {
		
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.tokenValidityInMilliseconds);
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key,SignatureAlgorithm.HS512)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
	}
	
	public String createAdminRefreshToken(Authentication authentication) {
		Claims claims = Jwts.claims().setSubject(authentication.getName());
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.tokenValidityInMilliseconds);
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(authentication.getName())
				.setClaims(claims)
				.signWith(key,SignatureAlgorithm.HS512)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
	}
	

	// 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) throws Exception {
    	JwtServiceResult<Claims> result = getValidateToken(token);
    	UserDetails principal = adminService.loadUserByUsername(result.getElement().getSubject());
    	return new UsernamePasswordAuthenticationToken(principal,"",principal.getAuthorities());
    }
	
	/**
	 * 커스텀 토큰 생성
	 * @param map
	 * @return
	 */
	public String createCustomToken(Map<String,Object> map,String subject) {
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.tokenValidityInMilliseconds);		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(subject)
				.setClaims(map)
				.signWith(key,SignatureAlgorithm.HS512)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
	}
	
	//
	public String createCustomRefreshToken(Claims claims,String subject) {
		
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.tokenValidityInMilliseconds);
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setSubject(subject)
				.setClaims(claims)
				.signWith(key,SignatureAlgorithm.HS512)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
	}
	
	
	// 토큰 유효성 검사 결과 데이터
    public JwtServiceResult<Claims> getValidateToken(String token) {
    	Claims claims  = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            logger.debug("#### {} ",claims);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_FAIL);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_EXPIRE);
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_NOTSUPPORT);
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_WRONG);
        }
        return new JwtServiceResult<>(JwtResultCodeType.TOKEN_OK,claims);
    }
	
}
