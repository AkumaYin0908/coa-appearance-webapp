package com.coa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoaappearanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoaappearanceApplication.class, args);
	}

}
