package com.astraser.code.challenge.movies.mapper;

import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MoviesMapper {

    public MovieDto mapToMovieDto(Movie movie, MovieDto movieDto) {
        movieDto.setId(movie.getId());
        movieDto.setDescription(movie.getDescription());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setTitle(movie.getTitle());
        movieDto.setRating(movie.getRating());
        return movieDto;
    }

    public Movie mapToMovie(MovieDto movieDto, Movie movie) {
        movie.setId(movieDto.getId());
        movie.setDescription(movieDto.getDescription());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setTitle(movieDto.getTitle());
        movie.setRating(movieDto.getRating());
        return movie;
    }
}
