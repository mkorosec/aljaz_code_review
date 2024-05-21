package com.astraser.code.challenge.movies.utils;

import com.astraser.code.challenge.movies.dto.ActorDto;
import com.astraser.code.challenge.movies.dto.MovieDto;
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
public class MoviesTestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public void setupMockActorMovie(WireMockServer mockService) throws IOException {

        String responseBody = objectMapper.writeValueAsString(createActorDtoList());

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

    public MovieDto createMovieDto(Long id, Double rating, LocalDate releaseDate) {
        MovieDto movie = new MovieDto();
        movie.setId(id);
        movie.setTitle("Title " + id);
        movie.setDescription("Description " + id);
        movie.setRating(rating);
        movie.setReleaseDate(releaseDate);
        movie.setActors(createActorDtoList());
        return movie;
    }

    public ActorDto createActorDto(Long id, String gender, LocalDate birthDate) {
        ActorDto actor = new ActorDto();
        actor.setId(id);
        actor.setFirstName("First Name Test " + id);
        actor.setLastName("Last Name Test " + id);
        actor.setGender(gender);
        actor.setBirthDate(birthDate);
        return actor;
    }


    public Collection<MovieDto> createMovieDtoList() {
        MovieDto movieDto1 = createMovieDto(1L, 5.0, LocalDate.of(2024, 1, 1));
        MovieDto movieDto2 = createMovieDto(2L, 5.5, LocalDate.of(2024, 2, 2));
        MovieDto movieDto3 = createMovieDto(3L, 6.0, LocalDate.of(2024, 3, 3));
        MovieDto movieDto4 = createMovieDto(4L, 6.5, LocalDate.of(2024, 4, 4));
        MovieDto mo0vieDto5 = createMovieDto(5L, 7.0, LocalDate.of(2024, 5, 5));
        MovieDto mo0vieDto6 = createMovieDto(6L, 7.5, LocalDate.of(2024, 6, 6));
        MovieDto mo0vieDto7 = createMovieDto(7L, 8.0, LocalDate.of(2024, 7, 7));

        return Arrays.asList(movieDto1, movieDto2, movieDto3, movieDto4, mo0vieDto5, mo0vieDto6, mo0vieDto7);
    }

    public Collection<ActorDto> createActorDtoList() {
        ActorDto actorDto1 = createActorDto(1L, "MALE", LocalDate.of(1989, 1, 1));
        ActorDto actorDto2 = createActorDto(2L, "FEMALE", LocalDate.of(1990, 1, 1));

        return List.of(actorDto1, actorDto2);
    }

    public void validateMovie(MovieDto updated, MovieDto original) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(updated.getTitle(), original.getTitle());
        assertEquals(updated.getDescription(), original.getDescription());
        assertEquals(updated.getRating(), original.getRating());
        assertEquals(updated.getReleaseDate(), original.getReleaseDate());
    }

    public void validateMovieActors(MovieDto movieDto) {
        assertNotNull(movieDto.getActors());
        assertEquals(movieDto.getActors().size(), 2);

        List<ActorDto> actorDtoList = new ArrayList<>(movieDto.getActors());
        assertEquals(actorDtoList.get(0).getFirstName(), "First Name Test 1");
        assertEquals(actorDtoList.get(0).getLastName(), "Last Name Test 1");
        assertEquals(actorDtoList.get(0).getBirthDate(), LocalDate.of(1989, 1, 1));
        assertEquals(actorDtoList.get(0).getGender(), "MALE");

        assertEquals(actorDtoList.get(1).getFirstName(), "First Name Test 2");
        assertEquals(actorDtoList.get(1).getLastName(), "Last Name Test 2");
        assertEquals(actorDtoList.get(1).getBirthDate(), LocalDate.of(1990, 1, 1));
        assertEquals(actorDtoList.get(1).getGender(), "FEMALE");


    }
}
