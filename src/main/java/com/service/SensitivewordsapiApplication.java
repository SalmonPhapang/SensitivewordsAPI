package com.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class SensitivewordsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensitivewordsapiApplication.class, args);
	}

}
