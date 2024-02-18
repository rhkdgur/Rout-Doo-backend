package com.routdoo.dailyroutine.auth.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Cors 설정 
 * @author GAMJA
 *	Cors 문제 해결 처리
 */
@Configuration
public class CorsFilterConfig {

	@Bean
	CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(List.of("http://localhost:3000","http://localhost:8080","http://routidoo.store","https://routidoo.store","https://routidoo001.cafe24.com"));
		config.setAllowedMethods(List.of(HttpMethod.POST.name(),HttpMethod.GET.name()));
		config.addAllowedHeader("*");
		config.setMaxAge(3000L); //캐싱 시간

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
