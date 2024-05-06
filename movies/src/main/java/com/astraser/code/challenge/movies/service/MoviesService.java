package com.astraser.code.challenge.movies.service;

import com.astraser.code.challenge.movies.dto.MovieDto;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface MoviesService {

    /**
     *
     * @param movieDto - Movie details
     */
    MovieDto create(MovieDto movieDto);

    /**
     *
     * @param id - Unique id of movie
     * @param includeActors - Flag to define if actors should be returned
     * @return Movie details based on a given id
     */
    MovieDto read(Long id, boolean includeActors);

    /**
     *
     * @return List of movies by pagination
     */
    Page<MovieDto> listPaginated(Integer page, Integer size);

    /**
     *
     * @return List of movies
     */
    Collection<MovieDto> list();

    /**
     *
     * @param movieDto - Movie details
     * @return Updated movie details based on given id and body
     */
    MovieDto update(Long id, MovieDto movieDto);

    /**
     *
     * @param id - Unique id of movie
     */
    void delete(Long id);
}
