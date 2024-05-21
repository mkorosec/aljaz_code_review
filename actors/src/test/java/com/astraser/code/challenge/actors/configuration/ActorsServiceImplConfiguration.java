package com.astraser.code.challenge.actors.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ActorsServiceImplConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer actorMoviesService() {
        return new WireMockServer(9999);
    }


}
