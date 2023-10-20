package com.routdoo.dailyroutine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.config
* @fileName      : RedisConfig.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.20
* @description   : Redis 환경 설정
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.20        ghgo       최초 생성
 */
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host,port);
	}
	
}
