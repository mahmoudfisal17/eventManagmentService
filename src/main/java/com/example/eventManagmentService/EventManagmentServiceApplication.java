package com.example.eventManagmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EventManagmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagmentServiceApplication.class, args);
	}

}
