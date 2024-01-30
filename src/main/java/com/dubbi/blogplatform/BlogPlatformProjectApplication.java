package com.dubbi.blogplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaAuditing
@EnableWebSecurity
public class BlogPlatformProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogPlatformProjectApplication.class, args);
	}

}
