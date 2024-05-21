package com.astraser.code.challenge.actors.movies.configuration;

import com.astraser.code.challenge.actors.movies.mapper.ActorsMoviesMapper;
import com.astraser.code.challenge.actors.movies.utils.ActorsMoviesTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ActorsMoviesTestConfiguration {

    @Bean
    public ActorsMoviesMapper actorMovieMapper() {
        return new ActorsMoviesMapper();
    }

    @Bean
    public ActorsMoviesTestUtils actorsTestUtils() {
        return new ActorsMoviesTestUtils();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
