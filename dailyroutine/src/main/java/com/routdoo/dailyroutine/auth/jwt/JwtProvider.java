package com.routdoo.dailyroutine.auth.jwt;

import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.SimpleDateFormat;
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
		this.tokenValidityInMilliseconds = Duration.ofDays(1).toMillis();
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
	
	public String createRefreshToken(Authentication authentication,boolean isUser) {
		
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Date now = new Date();
		long time = now.getTime();
		Date validity = new Date(time + this.refreshTokenValidityInMilliseconds);
		String AUTHORITIES_KEY = "";
		String id = "";
		if(isUser) {
			id = "user";
			AUTHORITIES_KEY = AUTHORITIES_U_KEY;
		}else{
			id = "admin";
			AUTHORITIES_KEY = AUTHORITIES_A_KEY;
		}
		
		return  Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setIssuer(id)
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key,SignatureAlgorithm.HS256)
				.setIssuedAt(now)
				.setExpiration(validity)
				.compact();
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
        	logger.debug("### key : "+key+","+token);
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            logger.debug("#### {} ",claims);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.error("잘못된 JWT 서명입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_FAIL);
        } catch (ExpiredJwtException e) {
            logger.error("만료된 JWT 토큰입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_EXPIRE);
        } catch (UnsupportedJwtException e) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_NOTSUPPORT);
        } catch (IllegalArgumentException e) {
            logger.error("JWT 토큰이 잘못되었습니다.");
            return new JwtServiceResult<>(JwtResultCodeType.TOKEN_WRONG);
        }
        return new JwtServiceResult<>(JwtResultCodeType.TOKEN_OK,claims);
    }
	
}
