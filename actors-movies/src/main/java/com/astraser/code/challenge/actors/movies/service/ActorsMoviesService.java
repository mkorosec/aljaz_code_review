package com.astraser.code.challenge.actors.movies.service;

import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;

import java.util.Collection;


public interface ActorsMoviesService {

    /**
     * @param movieId
     * @returns List of actors for specific movie by movie id
     */
    Collection<ActorDto> getMovieActors(Long movieId);

    /**
     * @param actorId
     * @return List of movies for specific actor by actor id
     */
    Collection<MovieDto> getActorMovies(Long actorId);


    /**
     * @param actorMovieDto
     */
    ActorMovieDto createActorMovie(ActorMovieDto actorMovieDto);


    /**
     * @param actorMovieDto
     */
    void deleteActorMovie(ActorMovieDto actorMovieDto);


    /**
     * @param actorId
     */
    void deleteActorMovieByActorId(Long actorId);

    /**
     * @param movieId
     */
    void deleteActorMovieByMovieId(Long movieId);

}
