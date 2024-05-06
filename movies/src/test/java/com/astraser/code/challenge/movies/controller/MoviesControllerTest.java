package com.astraser.code.challenge.movies.controller;

import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.service.MoviesService;
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
@WebMvcTest(MoviesController.class)
@AutoConfigureMockMvc
class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoviesService moviesService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getMovie() throws Exception {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Title");
        movieDto.setDescription("Description");
        movieDto.setReleaseDate(LocalDate.now());
        movieDto.setRating(7.5);

        when(moviesService.read(movieId,true)).thenReturn(movieDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.rating").value("7.5"))

        ;
    }

    @Test
    public void createMovie() throws Exception {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Title");
        movieDto.setDescription("Description");
        movieDto.setReleaseDate(LocalDate.now());
        movieDto.setRating(7.5);

        when(moviesService.create(movieDto)).thenReturn(movieDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.rating").value("7.5"))

        ;
    }

    @Test
    public void updateActor() throws Exception {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Title");
        movieDto.setDescription("Description");
        movieDto.setReleaseDate(LocalDate.now());
        movieDto.setRating(7.5);

        when(moviesService.update(movieId, movieDto)).thenReturn(movieDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/{id}",movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.rating").value("7.5"))

        ;
    }
    @Test
    public void testListActorsWithoutPagination() throws Exception {
        when(moviesService.list()).thenReturn(createMoviesList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Title"))
                .andExpect(jsonPath("$.[0].description").value("Description"))
                .andExpect(jsonPath("$.[0].releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.[0].rating").value("7.5"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Title 2"))
                .andExpect(jsonPath("$.[1].description").value("Description 2"))
                .andExpect(jsonPath("$.[1].releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.[1].rating").value("8.5"));
    }

    @Test
    public void testListActorsWithPagination() throws Exception {
        int page = 0;
        int size = 10;

        when(moviesService.listPaginated(page, size)).thenReturn(new PageImpl<>(createMoviesList().stream().toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Title"))
                .andExpect(jsonPath("$.content[0].description").value("Description"))
                .andExpect(jsonPath("$.content[0].releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.content[0].rating").value("7.5"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Title 2"))
                .andExpect(jsonPath("$.content[1].description").value("Description 2"))
                .andExpect(jsonPath("$.content[1].releaseDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.content[1].rating").value("8.5"));
        ;
    }


    private Collection<MovieDto> createMoviesList()
    {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Title");
        movieDto.setDescription("Description");
        movieDto.setReleaseDate(LocalDate.now());
        movieDto.setRating(7.5);

        Long movieId2 = 2L;
        MovieDto movieDto2 = new MovieDto();
        movieDto2.setId(movieId2);
        movieDto2.setTitle("Title 2");
        movieDto2.setDescription("Description 2");
        movieDto2.setReleaseDate(LocalDate.now());
        movieDto2.setRating(8.5);

        return Arrays.asList(movieDto,movieDto2);
    }

}