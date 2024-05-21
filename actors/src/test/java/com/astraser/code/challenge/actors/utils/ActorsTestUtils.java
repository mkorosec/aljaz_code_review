package com.astraser.code.challenge.actors.utils;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.dto.MovieDto;
import com.astraser.code.challenge.actors.entity.Actor;
import com.astraser.code.challenge.actors.enums.Gender;
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
public class ActorsTestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

        String responseBody = objectMapper.writeValueAsString(createMovieDtosList());

        mockService.stubFor(WireMock.get(WireMock.urlMatching("/api/v1/actors/\\d+/movies"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));
    }

    public void setupMockActorMovieDelete(WireMockServer mockService) {

        mockService.stubFor(WireMock.delete(WireMock.urlMatching("/api/v1/actors/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }


    public ActorDto createActorDto(Long id) {
        ActorDto actor = new ActorDto();
        actor.setId(id);
        actor.setFirstName("First Name Test " + id);
        actor.setLastName("Last Name Test " + id);
        actor.setGender(Gender.MALE);
        actor.setBirthDate(LocalDate.of(1989, 1, 1));
        actor.setMovies(createMovieDtosList());
        return actor;
    }

    public Actor createActor(Long id) {
        Actor actor = new Actor();
        actor.setId(id);
        actor.setFirstName("First Name Test " + id);
        actor.setLastName("Last Name Test " + id);
        actor.setGender(Gender.MALE);
        actor.setBirthDate(LocalDate.of(1989, 1, 1));
        return actor;
    }

    public MovieDto createMovieDto(Long id, Double rating, LocalDate releaseDate) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setTitle("Title " + id);
        movieDto.setDescription("Description " + id);
        movieDto.setRating(rating);
        movieDto.setReleaseDate(releaseDate);

        return movieDto;
    }

    public Collection<ActorDto> createActorDtosList() {
        ActorDto actorDto = createActorDto(1L);
        ActorDto actorDto2 = createActorDto(2L);
        ActorDto actorDto3 = createActorDto(3L);
        ActorDto actorDto4 = createActorDto(4L);
        ActorDto actorDto5 = createActorDto(5L);
        ActorDto actorDto6 = createActorDto(6L);
        ActorDto actorDto7 = createActorDto(7L);

        return Arrays.asList(actorDto, actorDto2, actorDto3, actorDto4, actorDto5, actorDto6, actorDto7);
    }

    public Collection<MovieDto> createMovieDtosList() {
        MovieDto movieDto1 = createMovieDto(1L, 8.0, LocalDate.of(2024, 1, 1));
        MovieDto movieDt2 = createMovieDto(2L, 9.5, LocalDate.of(2024, 2, 2));

        return List.of(movieDto1, movieDt2);
    }

    public void validateActor(ActorDto updated, ActorDto original) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(updated.getFirstName(), original.getFirstName());
        assertEquals(updated.getLastName(), original.getLastName());
        assertEquals(updated.getBirthDate(), original.getBirthDate());
        assertEquals(updated.getGender(), original.getGender());
    }


    public void validateActorMovies(ActorDto actorDto) {
        assertNotNull(actorDto.getMovies());
        assertEquals(actorDto.getMovies().size(), 2);

        List<MovieDto> actorDtoList = new ArrayList<>(actorDto.getMovies());
        assertEquals(actorDtoList.get(0).getTitle(), "Title 1");
        assertEquals(actorDtoList.get(0).getDescription(), "Description 1");
        assertEquals(actorDtoList.get(0).getRating(), 8.0);
        assertEquals(actorDtoList.get(0).getReleaseDate(), LocalDate.of(2024, 1, 1));

        assertEquals(actorDtoList.get(1).getTitle(), "Title 2");
        assertEquals(actorDtoList.get(1).getDescription(), "Description 2");
        assertEquals(actorDtoList.get(1).getRating(), 9.5);
        assertEquals(actorDtoList.get(1).getReleaseDate(), LocalDate.of(2024, 2, 2));


    }

}
