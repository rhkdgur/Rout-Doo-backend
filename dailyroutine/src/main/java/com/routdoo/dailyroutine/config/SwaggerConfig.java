package com.routdoo.dailyroutine.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger api
 * @author GAMJA
 *
 */
@Configuration
public class SwaggerConfig {
	
	private static final String API_NAME = "rout!doo backend api";
	private static final String API_VERSION = "1.0.0";
	private static final String API_DESCRIPTION = "routdoo API 명세서";
	
	@Bean
	public Docket api() {
		Parameter parameterBuider = new ParameterBuilder()
				.name(HttpHeaders.AUTHORIZATION)
				.description("Access Token")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false)
				.build();
		
		List<Parameter> globalParameters = new ArrayList<>();
		globalParameters.add(parameterBuider);
		
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(globalParameters)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.routdoo.dailyroutine"))
				.paths(PathSelectors.any())
				.build();
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().
				title(API_NAME)
				.version(API_VERSION)
				.description(API_DESCRIPTION)
				.build();
	}
}
