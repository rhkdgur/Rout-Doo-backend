package com.routdoo.dailyroutine.auth.jwt;

import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;

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
	
	private final JwtTokenService jwtTokenService;
	
	protected final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	protected final String AUTHORITIES_A_KEY = "routdoo-admin-auth";
	protected final String AUTHORITIES_U_KEY = "routdoo-user-auth";
	
	private String secret;
	
	/**토큰*/
	private long tokenValidityInMilliseconds;

	/**리플레쉬 토큰*/
	private long refreshTokenValidityInMilliseconds;

	private Key key;
	
	@PostConstruct
	protected void init() {
		this.secret = jwtProperties.getSecretkey();
//		this.tokenValidityInMilliseconds = Duration.ofMinutes(jwtProperties.getAccessTokenValidityInSeconds() % 60).toMillis();
		this.tokenValidityInMilliseconds = Duration.ofMinutes(30).toMillis();
		this.refreshTokenValidityInMilliseconds = Duration.ofDays(7).toMillis();
		//시크릿 값을 decode해서 키 변수에 할당
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	
	/**
	 * 권한 토큰 생성
	 * @param authentication
	 * @return
	 */
	public String createToken(Authentication authentication,boolean isUser) {
		
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.tokenValidityInMilliseconds);
		String AUTHORITIES_KEY = "";
		String id = "";
		if(isUser) {
			id = "user";
			AUTHORITIES_KEY = AUTHORITIES_U_KEY;
		}else{
			id = "admin";
			AUTHORITIES_KEY = AUTHORITIES_A_KEY;
		}
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setIssuer(id)
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key,SignatureAlgorithm.HS256)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
	}
	
	public String createRefreshToken(boolean isUser) {

		Claims claims = Jwts
				.claims();
		
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.refreshTokenValidityInMilliseconds);
		String id = "";
		if(isUser) {
			id = "user";
		}else{
			id = "admin";
		}
		
		return  Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setIssuer(id)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	

	// 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) throws Exception {
    	JwtServiceResult<Claims> result = getValidateToken(token);
    	UserDetails principal = null;
    	if(result.getElement().getIssuer().equals("admin")) {
    		principal = jwtTokenService.loadAdminByUsername(result.getElement().getSubject());
    	}else {
    		principal = jwtTokenService.loadUserByUsername(result.getElement().getSubject());
    	}
    	return new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
    }

	
	// 토큰 유효성 검사 결과 데이터
    public JwtServiceResult<Claims> getValidateToken(String token) {
    	Claims claims  = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException | MalformedJwtException e) {
            return new JwtServiceResult<>(JwtResultCodeType.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            return new JwtServiceResult<>(JwtResultCodeType.EXPIRED_TOKEN,e.getClaims());
        } catch (UnsupportedJwtException e) {
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_NOTSUPPORT);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_WRONG);
        }
        return new JwtServiceResult<>(JwtResultCodeType.TOKEN_OK,claims);
    }

	public Claims validateTokenCheck(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	//토큰 만료여부
	public boolean isExpired(String jwtToken) {
		Claims claims  = null;
		try {
			claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
		} catch (ExpiredJwtException expired) {	//토큰 기간이
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
