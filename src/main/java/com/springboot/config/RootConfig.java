package com.springboot.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
	
	@Bean
	public ModelMapper getModelMapper() {
		
		ModelMapper mapper = new ModelMapper();
		
		mapper.getConfiguration().
			setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setMatchingStrategy(MatchingStrategies.STRICT);
		
		return mapper;
		
	}
}
