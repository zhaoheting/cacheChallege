package com.ambicious.accessChallege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AccessChallegeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessChallegeApplication.class, args);
	}

}
