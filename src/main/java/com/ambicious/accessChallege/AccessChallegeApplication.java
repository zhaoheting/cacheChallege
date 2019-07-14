package com.ambicious.accessChallege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan
public class AccessChallegeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessChallegeApplication.class, args);
	}

}
