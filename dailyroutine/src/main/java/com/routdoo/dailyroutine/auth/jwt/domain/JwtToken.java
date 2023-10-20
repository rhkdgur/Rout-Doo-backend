package com.routdoo.dailyroutine.auth.jwt.domain;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Getter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.domain
* @fileName      : JwtToken.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.20
* @description   : jwt 토큰 관리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.20        ghgo       최초 생성
 */
@Getter
@RedisHash(value="jwtToken", timeToLive = 1800) //30분 토큰세션
public class JwtToken {

	@Id
	/**일련번호*/
	private String id;
	
	/**토큰*/
	private String token;
	
	/**생성일자*/
	private LocalDateTime createDate;
	
	
	public void addJwtToken(String id, String token, LocalDateTime createDate) {
		this.id = id;
		this.token = token;
		this.createDate = createDate;
	}
 
}
