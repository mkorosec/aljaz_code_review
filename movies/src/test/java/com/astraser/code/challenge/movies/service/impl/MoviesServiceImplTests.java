package com.astraser.code.challenge.movies.service.impl;

import com.astraser.code.challenge.movies.configuration.MoviesServiceImplConfiguration;
import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.exception.MovieNotFoundException;
import com.astraser.code.challenge.movies.service.MoviesService;
import com.astraser.code.challenge.movies.service.client.ActorMovieClient;
import com.astraser.code.challenge.movies.utils.MoviesTestUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.transaction.Transactional;
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
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MoviesServiceImplConfiguration.class})
@ActiveProfiles("test")
@Transactional
class MoviesServiceImplTests {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private ActorMovieClient actorMovieClient;

    @Autowired
    private MoviesTestUtils moviesTestUtils;

    @BeforeEach
    void setUp() throws IOException {
        moviesTestUtils.setupMockActorMovie(wireMockServer);
    }

    @Test
    void testCreateUpdateDeleteMovie() {

        MovieDto movie = moviesTestUtils.createMovieDto(1L, 8.0, LocalDate.of(2024, 1, 1));
        MovieDto created = moviesService.create(movie);

        moviesTestUtils.validateMovie(movie, created);

        //Update movie
        created.setTitle("Updated Title 1");
        created.setDescription("Updated Description 1");
        created.setRating(8.5);
        created.setReleaseDate(LocalDate.of(2024, 2, 2));

        moviesService.update(created.getId(), created);

        MovieDto updated = moviesService.read(created.getId(), true);

        moviesTestUtils.validateMovie(created, updated);
        moviesTestUtils.validateMovieActors(updated);

        moviesService.delete(updated.getId());

        assertThrows(MovieNotFoundException.class, () -> moviesService.read(updated.getId(), true));


    }


    @Test
    void testRetrieveMoviesPaginated() {

        List<MovieDto> movies = moviesTestUtils.createMovieDtoList().stream().toList();
        movies.forEach(a -> {
            MovieDto movieDto = moviesService.create(a);
            a.setId(movieDto.getId());
        });

        Page<MovieDto> paginatedListFirst = moviesService.listPaginated(0, 5);

        assertNotNull(paginatedListFirst.getContent());
        assertEquals(5, paginatedListFirst.getContent().size());

        assertArrayEquals(movies.stream().limit(5).toArray(), paginatedListFirst.getContent().toArray());

        Page<MovieDto> paginatedListSecond = moviesService.listPaginated(1, 5);

        assertNotNull(paginatedListSecond.getContent());
        assertEquals(2, paginatedListSecond.getContent().size());

        assertArrayEquals(movies.subList(5, 7).toArray(), paginatedListSecond.getContent().toArray());


    }

    @Test
    void testRetrieveMovies() {
        List<MovieDto> movies = moviesTestUtils.createMovieDtoList().stream().toList();
        movies.forEach(a -> {
            MovieDto movieDto = moviesService.create(a);
            a.setId(movieDto.getId());
        });

        Collection<MovieDto> list = moviesService.list();
        assertEquals(7, list.size());
        assertArrayEquals(movies.toArray(), list.toArray());

    }


}