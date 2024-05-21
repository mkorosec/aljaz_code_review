package com.astraser.code.challenge.actors.movies.configuration;

import com.astraser.code.challenge.actors.movies.utils.ActorsMoviesTestUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ActorsMoviesServiceImplConfiguration {


    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer actorMoviesService() {
        return new WireMockServer(9999);
    }

    @Bean
    public ActorsMoviesTestUtils actorsMoviesTestUtils() {
        return new ActorsMoviesTestUtils();
    }

}
