package com.astraser.code.challenge.movies.service.impl;

import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.entity.Movie;
import com.astraser.code.challenge.movies.exception.MovieAlreadyExistsException;
import com.astraser.code.challenge.movies.exception.MovieNotFoundException;
import com.astraser.code.challenge.movies.exception.MovieValidationException;
import com.astraser.code.challenge.movies.helper.ActorMovieClientHelper;
import com.astraser.code.challenge.movies.mapper.MoviesMapper;
import com.astraser.code.challenge.movies.repository.MoviesRepository;
import com.astraser.code.challenge.movies.service.MoviesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class MoviesServiceImpl implements MoviesService {

    private MoviesRepository moviesRepository;
    private MoviesMapper moviesMapper;
    private ActorMovieClientHelper actorMovieClientHelper;


    @Override
    public MovieDto create(MovieDto movieDto) {
        moviesRepository.findByTitle(movieDto.getTitle())
                .ifPresent(actor -> {
                    throw new MovieAlreadyExistsException(String.format("Movie with title %s already exists.", movieDto.getTitle()));
                });
        Movie movie = moviesRepository.save(moviesMapper.mapToMovie(movieDto, new Movie()));
        return moviesMapper.mapToMovieDto(movie, new MovieDto());
    }

    @Override
    public MovieDto read(Long id, boolean includeActors) {
        MovieDto movieDto = moviesRepository.findById(id).map(movie -> moviesMapper.mapToMovieDto(movie, new MovieDto())).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with id %s not found.", id)));
        if (includeActors) {
            actorMovieClientHelper.getMovieActors(movieDto);
        }
        return movieDto;

    }


    @Override
    public MovieDto update(Long id, MovieDto movieDto) {
        if (!id.equals(movieDto.getId())) {
            throw new MovieValidationException(String.format("Request id {} is not matching body id: {}", id, movieDto.getId()));
        }
        Movie existingMovie = moviesRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with id %s not found.", id)));
        moviesMapper.mapToMovie(movieDto, existingMovie);
        moviesRepository.save(existingMovie);
        return moviesMapper.mapToMovieDto(existingMovie, new MovieDto());
    }

    @Override
    public void delete(Long id) {
        Movie actor = moviesRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with id %s not found.", id)));
        moviesRepository.delete(actor);
        actorMovieClientHelper.deleteMovieActors(actor.getId());
    }


    @Override
    public Page<MovieDto> listPaginated(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Movie> moviePage = moviesRepository.findAll(paging);
        return moviePage.map(movie -> {
            MovieDto movieDto = moviesMapper.mapToMovieDto(movie, new MovieDto());
            actorMovieClientHelper.getMovieActors(movieDto);
            return movieDto;
        });
    }

    @Override
    public Collection<MovieDto> list() {
        return moviesRepository.findAll().stream().map(movie -> {
            MovieDto movieDto = moviesMapper.mapToMovieDto(movie, new MovieDto());
            actorMovieClientHelper.getMovieActors(movieDto);
            return movieDto;
        }).toList();
    }


}
