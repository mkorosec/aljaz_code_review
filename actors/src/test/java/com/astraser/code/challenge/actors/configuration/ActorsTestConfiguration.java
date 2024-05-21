package com.astraser.code.challenge.actors.configuration;

import com.astraser.code.challenge.actors.mapper.ActorsMapper;
import com.astraser.code.challenge.actors.utils.ActorsTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ActorsTestConfiguration {

    @Bean
    public ActorsMapper actorsMapper() {
        return new ActorsMapper();
    }

    @Bean
    public ActorsTestUtils actorsTestUtils() {
        return new ActorsTestUtils();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
