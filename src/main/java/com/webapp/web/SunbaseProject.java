package com.webapp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SunbaseProject {

	public static void main(String[] args) {
		SpringApplication.run(SunbaseProject.class, args);
	}

}
