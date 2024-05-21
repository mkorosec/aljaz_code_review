package com.astraser.code.challenge.movies.configuration;

import com.astraser.code.challenge.movies.mapper.MoviesMapper;
import com.astraser.code.challenge.movies.utils.MoviesTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MoviesTestConfiguration {

    @Bean
    public MoviesMapper actorsMapper() {
        return new MoviesMapper();
    }

    @Bean
    public MoviesTestUtils actorsTestUtils() {
        return new MoviesTestUtils();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
