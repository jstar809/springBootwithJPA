package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		 
		return new Docket(DocumentationType.OAS_30)
				.useDefaultResponseMessages(false)
				 .directModelSubstitute(MultipartFile.class, String.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.springboot.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfoBuilder().title("bott project swagger").build());
	}
}
