package com.astraser.code.challenge.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator accountMovieRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route(p -> p
                        .path("/code-challenge/actors/**")
                        .filters(f -> f.rewritePath("/code-challenge/actors/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACTORS"))
                .route(p -> p
                        .path("/code-challenge/movies/**")
                        .filters(f -> f.rewritePath("/code-challenge/movies/(?<segment>.*)", "/${segment}"))
                        .uri("lb://MOVIES"))
                .route(p -> p
                        .path("/code-challenge/actor-movie/**")
                        .filters(f -> f.rewritePath("/code-challenge/actor-movie/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACTOR-MOVIE"))
                .route(p -> p
                        .path("/code-challenge/gateway/**")
                        .filters(f -> f.rewritePath("/code-challenge/gateway/(?<segment>.*)", "/${segment}"))
                        .uri("lb://GATEWAYSERVER"))

                .build();

    }
}
