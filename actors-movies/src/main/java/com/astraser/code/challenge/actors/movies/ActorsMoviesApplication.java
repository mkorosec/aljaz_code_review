package com.astraser.code.challenge.actors.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
public class ActorsMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActorsMoviesApplication.class, args);
	}

}