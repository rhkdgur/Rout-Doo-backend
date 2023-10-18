package com.routdoo.dailyroutine.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt
* @fileName      : JwtProperties.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.19
* @description   : jwt 프로퍼티 키값 가져오기
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.19        ghgo       최초 생성
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String header;
	
	private String secretKey;
	
	private Long accessTokenValidityInSeconds;
	
}
