package com.vivek.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfigurations {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
