package com.astraser.code.challenge.movies.utils;

import com.astraser.code.challenge.movies.dto.ActorDto;
import com.astraser.code.challenge.movies.dto.MovieDto;
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
public class MoviesHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("__files/actor-movie-actors-response.json");

        // Deserialize JSON content into a Collection of MovieDto
        Collection<ActorDto> actors = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        String responseBody = objectMapper.writeValueAsString(actors);

        mockService.stubFor(WireMock.get(WireMock.urlMatching("/api/v1/movies/\\d+/actors"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        mockService.stubFor(WireMock.delete(WireMock.urlMatching("/api/v1/movies/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    public MovieDto createMovieDto() {
        MovieDto movie = new MovieDto();
        movie.setId(1l);
        movie.setTitle("Title");
        movie.setDescription("Description");
        movie.setRating(8.0);
        movie.setReleaseDate(LocalDate.of(1989, 1, 1));
        return movie;
    }
}
