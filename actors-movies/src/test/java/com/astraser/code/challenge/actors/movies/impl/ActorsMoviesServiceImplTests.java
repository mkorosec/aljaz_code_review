package com.astraser.code.challenge.actors.movies.impl;

import com.astraser.code.challenge.actors.movies.configuration.ActorsMoviesServiceImplConfiguration;
import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import com.astraser.code.challenge.actors.movies.service.client.ActorsClient;
import com.astraser.code.challenge.actors.movies.service.client.MoviesClient;
import com.astraser.code.challenge.actors.movies.utils.ActorsMoviesTestUtils;
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
import java.util.Collection;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ActorsMoviesServiceImplConfiguration.class})
@ActiveProfiles("test")
class ActorsMoviesServiceImplTests {

    @Autowired
    private WireMockServer actorMoviesWiremock;


    @Autowired
    private ActorsClient actorsClient;

    @Autowired
    private MoviesClient moviesClient;

    @Autowired
    private ActorsMoviesService actorsService;

    @Autowired
    private ActorsMoviesTestUtils actorsMoviesTestUtils;

    @BeforeEach
    void setUp() throws IOException {
        actorsMoviesTestUtils.setupMockActorMovie(actorMoviesWiremock);
    }

    @Test
    void getMovieActors() {
        Long movieId = 1l;
        Collection<ActorDto> actors = actorsService.getMovieActors(movieId);
        actorsMoviesTestUtils.validateMovieActors(actors);
    }

    @Test
    void getActorMovies() {
        Long movieId = 1l;
        Collection<MovieDto> actorMovies = actorsService.getActorMovies(movieId);
        actorsMoviesTestUtils.validateActorMovies(actorMovies);
    }


}