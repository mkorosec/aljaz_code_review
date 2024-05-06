package com.astraser.code.challenge.actors.utils;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.dto.MovieDto;
import com.astraser.code.challenge.actors.enums.Gender;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class ActorsHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("__files/actor-movie-movies-response.json");

        // Deserialize JSON content into a Collection of MovieDto
        Collection<MovieDto> movies = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        String responseBody = objectMapper.writeValueAsString(movies);

        mockService.stubFor(WireMock.get(WireMock.urlMatching("/api/v1/actors/\\d+/movies"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        mockService.stubFor(WireMock.delete(WireMock.urlMatching("/api/v1/actors/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    public ActorDto createActor() {
        ActorDto actor = new ActorDto();
        actor.setId(1l);
        actor.setFirstName("First");
        actor.setLastName("Last");
        actor.setGender(Gender.MALE);
        actor.setBirthDate(LocalDate.of(1989, 1, 1));
        return actor;
    }
}
