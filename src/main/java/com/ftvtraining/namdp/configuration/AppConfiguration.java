package com.ftvtraining.namdp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ftvtraining.namdp")
public class AppConfiguration {
	@Bean
	public ModelMapper modealMapper() {
		return new ModelMapper();
	}
}
