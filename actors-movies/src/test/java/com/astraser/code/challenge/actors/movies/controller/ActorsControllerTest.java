package com.astraser.code.challenge.actors.movies.controller;

import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActorsMoviesController.class)
@AutoConfigureMockMvc
class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorsMoviesService actorsMoviesService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getActorMovies() throws Exception {
        Long actorId = 1L;

        when(actorsMoviesService.getActorMovies(actorId)).thenReturn(createMoviesList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/actors/{actorId}/movies", actorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Title"))
                .andExpect(jsonPath("$.[0].description").value("Description"))
                .andExpect(jsonPath("$.[0].releaseDate").value("2012-01-01"))
                .andExpect(jsonPath("$.[0].rating").value("7.5"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Title 2"))
                .andExpect(jsonPath("$.[1].description").value("Description 2"))
                .andExpect(jsonPath("$.[1].releaseDate").value("2020-01-01"))
                .andExpect(jsonPath("$.[1].rating").value("8.5"));
    }

    @Test
    public void getMovieActors() throws Exception {
        Long movieId = 1L;

        when(actorsMoviesService.getMovieActors(movieId)).thenReturn(createActorsList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movies/{movieId}/actors", movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].firstName").value("First name"))
                .andExpect(jsonPath("$.[0].lastName").value("Last name"))
                .andExpect(jsonPath("$.[0].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.[0].gender").value("MALE"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].firstName").value("First name 2"))
                .andExpect(jsonPath("$.[1].lastName").value("Last name 2"))
                .andExpect(jsonPath("$.[1].birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.[1].gender").value("FEMALE"));
    }

    @Test
    public void createActorMovie() throws Exception {
        ActorMovieDto actorMovieDto = new ActorMovieDto();
        actorMovieDto.setId(1l);
        actorMovieDto.setActorId(1l);
        actorMovieDto.setMovieId(1l);


        when(actorsMoviesService.createActorMovie(actorMovieDto)).thenReturn(actorMovieDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorMovieDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.actorId").value("1"))
                .andExpect(jsonPath("$.movieId").value("1"));
    }




    private Collection<ActorDto> createActorsList()
    {
        Long actorId = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorId);
        actorDto.setFirstName("First name");
        actorDto.setLastName("Last name");
        actorDto.setBirthDate("1989-01-01");
        actorDto.setGender("MALE");

        Long actorId2 = 2L;
        ActorDto actorDto2 = new ActorDto();
        actorDto2.setId(actorId2);
        actorDto2.setFirstName("First name 2");
        actorDto2.setLastName("Last name 2");
        actorDto2.setBirthDate("1990-01-01");
        actorDto2.setGender("FEMALE");

        return Arrays.asList(actorDto,actorDto2);
    }

    private Collection<MovieDto> createMoviesList()
    {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Title");
        movieDto.setDescription("Description");
        movieDto.setReleaseDate("2012-01-01");
        movieDto.setRating(7.5);

        Long movieId2 = 2L;
        MovieDto movieDto2 = new MovieDto();
        movieDto2.setId(movieId2);
        movieDto2.setTitle("Title 2");
        movieDto2.setDescription("Description 2");
        movieDto2.setReleaseDate("2020-01-01");
        movieDto2.setRating(8.5);

        return Arrays.asList(movieDto,movieDto2);
    }

}