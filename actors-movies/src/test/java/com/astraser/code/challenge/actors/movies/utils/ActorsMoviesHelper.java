package com.astraser.code.challenge.actors.movies.utils;

import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class ActorsMoviesHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

//        InputStream inputStreamActors = this.getClass().getClassLoader().getResourceAsStream("__files/actor-actors-response.json");
//
//        // Deserialize JSON content into a Collection of MovieDto
//        Collection<ActorDto> actors = objectMapper.readValue(inputStreamActors, new TypeReference<>() {
//        });

        String responseBody = objectMapper.writeValueAsString(createActor1());

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/1?includeMovies=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createActor2());

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/2?includeMovies=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createMovie());

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/1?includeActors=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createMovie2());

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/2?includeActors=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

    }

    public ActorMovieDto createActorMovieDto() {
        ActorMovieDto actorMovie = new ActorMovieDto();
        actorMovie.setId(1l);
        actorMovie.setActorId(1l);
        actorMovie.setMovieId(1l);
        return actorMovie;
    }


    public ActorDto createActor1() {
        ActorDto actor = new ActorDto();
        actor.setId(1l);
        actor.setFirstName("First Name 1");
        actor.setLastName("Last Name 1");
        actor.setGender("MALE");
        actor.setBirthDate("1989-01-01");
        return actor;
    }

    public ActorDto createActor2() {
        ActorDto actor = new ActorDto();
        actor.setId(2l);
        actor.setFirstName("First Name 2");
        actor.setLastName("Last Name 2");
        actor.setGender("FEMALE");
        actor.setBirthDate("1990-01-01");
        return actor;
    }

    public MovieDto createMovie() {
        MovieDto movie = new MovieDto();
        movie.setId(1l);
        movie.setTitle("Title 1");
        movie.setDescription("Description 1");
        movie.setRating(8.0);
        movie.setReleaseDate("2012-01-01");
        return movie;
    }

    public MovieDto createMovie2() {
        MovieDto movie = new MovieDto();
        movie.setId(2l);
        movie.setTitle("Title 2");
        movie.setDescription("Description 2");
        movie.setRating(8.5);
        movie.setReleaseDate("2020-01-01");
        return movie;
    }
}
