package com.astraser.code.challenge.movies.service.impl;

import com.astraser.code.challenge.movies.configuration.MoviesServiceImplConfiguration;
import com.astraser.code.challenge.movies.dto.ActorDto;
import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.exception.MovieNotFoundException;
import com.astraser.code.challenge.movies.service.MoviesService;
import com.astraser.code.challenge.movies.service.client.ActorMovieClient;
import com.astraser.code.challenge.movies.utils.MoviesHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MoviesServiceImplConfiguration.class})
@ActiveProfiles("test")
class MoviesServiceImplTests {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private ActorMovieClient actorMovieClient;

    @Autowired
    private MoviesHelper moviesHelper;

    @BeforeEach
    void setUp() throws IOException {
        moviesHelper.setupMockActorMovie(wireMockServer);
    }

    @Test
    void createUpdateAndDeleteMovie() {

        MovieDto movie = moviesHelper.createMovieDto();
        MovieDto created = moviesService.create(movie);

        validateMovie(movie, created);

        //Update movie
        created.setTitle("Updated Title");
        created.setDescription("Updated Description");
        created.setRating(8.5);
        created.setReleaseDate(LocalDate.of(1990, 1, 1));

        moviesService.update(created.getId(), created);

        MovieDto updated = moviesService.read(created.getId(),true);

        validateMovie(created, updated);
        validateMovieActors(updated);

        moviesService.delete(updated.getId());

        assertThrows(MovieNotFoundException.class, () -> moviesService.read(updated.getId(),true));
    }

    private void validateMovie(MovieDto updated, MovieDto original) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(updated.getTitle(), original.getTitle());
        assertEquals(updated.getDescription(), original.getDescription());
        assertEquals(updated.getRating(), original.getRating());
        assertEquals(updated.getReleaseDate(), original.getReleaseDate());
    }

    private void validateMovieActors(MovieDto movieDto) {
        assertNotNull(movieDto.getActors());
        assertEquals(movieDto.getActors().size(), 2);

        List<ActorDto> actorDtoList = new ArrayList<>(movieDto.getActors());
        assertEquals(actorDtoList.get(0).getFirstName(), "First Name");
        assertEquals(actorDtoList.get(0).getLastName(), "Last Name");
        assertEquals(actorDtoList.get(0).getBirthDate(), LocalDate.of(1989,12,31));
        assertEquals(actorDtoList.get(0).getGender(), "MALE");

        assertEquals(actorDtoList.get(1).getFirstName(), "Second Name");
        assertEquals(actorDtoList.get(1).getLastName(), "Last Name");
        assertEquals(actorDtoList.get(1).getBirthDate(),  LocalDate.of(1990,12,31));
        assertEquals(actorDtoList.get(1).getGender(), "FEMALE");


    }


}