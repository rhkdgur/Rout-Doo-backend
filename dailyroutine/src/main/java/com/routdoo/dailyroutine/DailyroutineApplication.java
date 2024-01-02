package com.routdoo.dailyroutine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//swagger cors 이슈에 대한 처리 방법 https 일때 http로 되는 경우가 있는데 이걸 통해 해결가능
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class DailyroutineApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DailyroutineApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DailyroutineApplication.class);
	}

}
