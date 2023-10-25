package com.routdoo.dailyroutine.auth.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.routdoo.dailyroutine.auth.jwt.JwtProvider;
import com.routdoo.dailyroutine.auth.jwt.filter.JwtAuthenticationFilter;
import com.routdoo.dailyroutine.auth.jwt.repository.JwtTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
//	private final CorsFilter corsFilter;
	
	private final JwtProvider jwtProvider;
	
	private final JwtTokenRepository jwtTokenRepository;
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return  http
		.httpBasic(AbstractHttpConfigurer::disable)
		.cors(AbstractHttpConfigurer::disable)
		.csrf(AbstractHttpConfigurer::disable)
//		.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling((exceptionHandling) ->
           exceptionHandling
           	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
           	.accessDeniedHandler(jwtAccessDeniedHandler)
		).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/mgn/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.GET,"/member/view").hasAuthority("USER")
				.requestMatchers(HttpMethod.POST,"/member/login").permitAll()
				.requestMatchers("/**").permitAll()
		).addFilterBefore(new JwtAuthenticationFilter(jwtProvider,jwtTokenRepository), UsernamePasswordAuthenticationFilter.class)
		.build();
		
	}
}
