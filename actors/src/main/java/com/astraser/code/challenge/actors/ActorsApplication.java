package com.astraser.code.challenge.actors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ActorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActorsApplication.class, args);
    }

}
