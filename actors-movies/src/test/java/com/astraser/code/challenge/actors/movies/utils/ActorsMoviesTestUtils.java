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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class ActorsMoviesTestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

        String responseBody = objectMapper.writeValueAsString(createActorDto(1L, LocalDate.of(1989, 1, 1), "MALE"));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/1?includeMovies=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createActorDto(2L, LocalDate.of(1990, 1, 1), "FEMALE"));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/2?includeMovies=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createMovieDto(1L, 8.0, LocalDate.of(2024, 1, 1)));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/1?includeActors=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

        responseBody = objectMapper.writeValueAsString(createMovieDto(2L, 9.0, LocalDate.of(2024, 2, 2)));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/2?includeActors=false"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));

    }


    public ActorDto createActorDto(Long id, LocalDate localDate, String gender) {
        ActorDto actor = new ActorDto();
        actor.setId(id);
        actor.setFirstName("First Name " + id);
        actor.setLastName("Last Name " + id);
        actor.setGender(gender);
        actor.setBirthDate(localDate);
        return actor;
    }

    public MovieDto createMovieDto(Long id, Double rating, LocalDate releaseDate) {
        MovieDto movie = new MovieDto();
        movie.setId(id);
        movie.setTitle("Title " + id);
        movie.setDescription("Description " + id);
        movie.setRating(rating);
        movie.setReleaseDate(releaseDate);
        return movie;
    }


    public Collection<ActorDto> createActorsList() {
        ActorDto actorDto1 = createActorDto(1L, LocalDate.of(1989, 1, 1), "MALE");
        ActorDto actorDto2 = createActorDto(2L, LocalDate.of(1990, 1, 1), "FEMALE");

        return Arrays.asList(actorDto1, actorDto2);
    }

    public Collection<MovieDto> createMoviesList() {
        MovieDto movieDto = createMovieDto(1L, 8.0, LocalDate.of(2024, 1, 1));
        MovieDto movieDto2 = createMovieDto(2L, 9.0, LocalDate.of(2024, 2, 2));

        return Arrays.asList(movieDto, movieDto2);
    }

    public ActorMovieDto createActorMovieDto(Long id) {
        ActorMovieDto actorMovieDto = new ActorMovieDto();
        actorMovieDto.setId(id);
        actorMovieDto.setMovieId(id);
        actorMovieDto.setActorId(id);

        return actorMovieDto;
    }


    public void validateActorMovies(Collection<MovieDto> actorMovies) {
        assertNotNull(actorMovies);
        assertEquals(actorMovies.size(), 2);

        List<MovieDto> actorMoviesList = new ArrayList<>(actorMovies);
        assertEquals(actorMoviesList.get(0).getTitle(), "Title 1");
        assertEquals(actorMoviesList.get(0).getDescription(), "Description 1");
        assertEquals(actorMoviesList.get(0).getRating(), 8.0);
        assertEquals(actorMoviesList.get(0).getReleaseDate(), LocalDate.of(2024, 1, 1));

        assertEquals(actorMoviesList.get(1).getTitle(), "Title 2");
        assertEquals(actorMoviesList.get(1).getDescription(), "Description 2");
        assertEquals(actorMoviesList.get(1).getRating(), 9.0);
        assertEquals(actorMoviesList.get(1).getReleaseDate(), LocalDate.of(2024, 2, 2));


    }

    public void validateMovieActors(Collection<ActorDto> movieActors) {
        assertNotNull(movieActors);
        assertEquals(movieActors.size(), 2);

        List<ActorDto> movieActorsList = new ArrayList<>(movieActors);
        assertEquals(movieActorsList.get(0).getFirstName(), "First Name 1");
        assertEquals(movieActorsList.get(0).getLastName(), "Last Name 1");
        assertEquals(movieActorsList.get(0).getGender(), "MALE");
        assertEquals(movieActorsList.get(0).getBirthDate(), LocalDate.of(1989, 1, 1));

        assertEquals(movieActorsList.get(1).getFirstName(), "First Name 2");
        assertEquals(movieActorsList.get(1).getLastName(), "Last Name 2");
        assertEquals(movieActorsList.get(1).getGender(), "FEMALE");
        assertEquals(movieActorsList.get(1).getBirthDate(), LocalDate.of(1990, 1, 1));


    }


}
