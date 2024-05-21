package com.astraser.code.challenge.actors.movies.controller;

import com.astraser.code.challenge.actors.movies.configuration.ActorsMoviesTestConfiguration;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import com.astraser.code.challenge.actors.movies.utils.ActorsMoviesTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActorsMoviesController.class)
@ContextConfiguration(classes = {ActorsMoviesTestConfiguration.class})
@AutoConfigureMockMvc
class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorsMoviesService actorsMoviesService;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ActorsMoviesTestUtils actorsMoviesTestUtils;

    @Test
    public void testGetActorMovies() throws Exception {
        Long actorId = 1L;

        when(actorsMoviesService.getActorMovies(actorId)).thenReturn(actorsMoviesTestUtils.createMoviesList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/actors/{actorId}/movies", actorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Title 1"))
                .andExpect(jsonPath("$.[0].description").value("Description 1"))
                .andExpect(jsonPath("$.[0].releaseDate").value("2024-01-01"))
                .andExpect(jsonPath("$.[0].rating").value("8.0"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Title 2"))
                .andExpect(jsonPath("$.[1].description").value("Description 2"))
                .andExpect(jsonPath("$.[1].releaseDate").value("2024-02-02"))
                .andExpect(jsonPath("$.[1].rating").value("9.0"));
    }

    @Test
    public void testGetMovieActors() throws Exception {
        Long movieId = 1L;

        when(actorsMoviesService.getMovieActors(movieId)).thenReturn(actorsMoviesTestUtils.createActorsList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movies/{movieId}/actors", movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].firstName").value("First Name 1"))
                .andExpect(jsonPath("$.[0].lastName").value("Last Name 1"))
                .andExpect(jsonPath("$.[0].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.[0].gender").value("MALE"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].firstName").value("First Name 2"))
                .andExpect(jsonPath("$.[1].lastName").value("Last Name 2"))
                .andExpect(jsonPath("$.[1].birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.[1].gender").value("FEMALE"));
    }

    @Test
    public void testCreateActorMovie() throws Exception {

        ActorMovieDto actorMovieDto = actorsMoviesTestUtils.createActorMovieDto(1L);
        when(actorsMoviesService.createActorMovie(actorMovieDto)).thenReturn(actorMovieDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorMovieDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.actorId").value("1"))
                .andExpect(jsonPath("$.movieId").value("1"));
    }


}