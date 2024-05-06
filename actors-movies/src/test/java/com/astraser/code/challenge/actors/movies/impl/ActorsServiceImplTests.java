package com.astraser.code.challenge.actors.movies.impl;

import com.astraser.code.challenge.actors.movies.configuration.ActorsMoviesServiceImplConfiguration;
import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import com.astraser.code.challenge.actors.movies.service.client.ActorsClient;
import com.astraser.code.challenge.actors.movies.service.client.MoviesClient;
import com.astraser.code.challenge.actors.movies.utils.ActorsMoviesHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ActorsMoviesServiceImplConfiguration.class})
@ActiveProfiles("test")
class ActorsServiceImplTests {

    @Autowired
    private WireMockServer actorMoviesWiremock;


    @Autowired
    private ActorsClient actorsClient;

    @Autowired
    private MoviesClient moviesClient;

    @Autowired
    private ActorsMoviesService actorsService;

    @Autowired
    private ActorsMoviesHelper actorsMoviesHelper;

    @BeforeEach
    void setUp() throws IOException {
        actorsMoviesHelper.setupMockActorMovie(actorMoviesWiremock);
    }


    @Test
    void getMovieActors() {
        Long movieId = 1l;
        Collection<ActorDto> actors = actorsService.getMovieActors(movieId);
        validateMovieActors(actors);
    }

    @Test
    void getActorMovies() {
        Long movieId = 1l;
        Collection<MovieDto> actorMovies = actorsService.getActorMovies(movieId);
        validateActorMovies(actorMovies);
    }


    private void validateActorMovies(Collection<MovieDto> actorMovies) {
        assertNotNull(actorMovies);
        assertEquals(actorMovies.size(), 2);

        List<MovieDto> actorMoviesList = new ArrayList<>(actorMovies);
        assertEquals(actorMoviesList.get(0).getTitle(), "Title 1");
        assertEquals(actorMoviesList.get(0).getDescription(), "Description 1");
        assertEquals(actorMoviesList.get(0).getRating(), 8.0);
        assertEquals(actorMoviesList.get(0).getReleaseDate(), "2012-01-01");

        assertEquals(actorMoviesList.get(1).getTitle(), "Title 2");
        assertEquals(actorMoviesList.get(1).getDescription(), "Description 2");
        assertEquals(actorMoviesList.get(1).getRating(), 8.5);
        assertEquals(actorMoviesList.get(1).getReleaseDate(), "2020-01-01");


    }

    private void validateMovieActors(Collection<ActorDto> movieActors) {
        assertNotNull(movieActors);
        assertEquals(movieActors.size(), 2);

        List<ActorDto> movieActorsList = new ArrayList<>(movieActors);
        assertEquals(movieActorsList.get(0).getFirstName(), "First Name 1");
        assertEquals(movieActorsList.get(0).getLastName(), "Last Name 1");
        assertEquals(movieActorsList.get(0).getGender(), "MALE");
        assertEquals(movieActorsList.get(0).getBirthDate(), "1989-01-01");

        assertEquals(movieActorsList.get(1).getFirstName(), "First Name 2");
        assertEquals(movieActorsList.get(1).getLastName(), "Last Name 2");
        assertEquals(movieActorsList.get(1).getGender(), "FEMALE");
        assertEquals(movieActorsList.get(1).getBirthDate(), "1990-01-01");


    }

}