package com.astraser.code.challenge.actors.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.discovery.EurekaClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
public class ActorsServiceImplConfiguration {


    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer actorMoviesService() {
        return new WireMockServer(9999);
    }

}
