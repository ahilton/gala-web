package com.zut.galaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GalaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaWebApplication.class, args);
	}
}
