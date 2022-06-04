package com.viniciuslo66.projetoed1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TodolistApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
