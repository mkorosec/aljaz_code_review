package com.astraser.code.challenge.actors.movies.service.impl;

import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.astraser.code.challenge.actors.movies.entity.ActorMovie;
import com.astraser.code.challenge.actors.movies.exception.ActorMovieAlreadyExistsException;
import com.astraser.code.challenge.actors.movies.mapper.ActorsMoviesMapper;
import com.astraser.code.challenge.actors.movies.repository.ActorsMoviesRepository;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import com.astraser.code.challenge.actors.movies.service.client.ActorsClient;
import com.astraser.code.challenge.actors.movies.service.client.MoviesClient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActorsMoviesServiceImpl implements ActorsMoviesService {

    static final Logger logger = LoggerFactory.getLogger(ActorsMoviesServiceImpl.class);
    private ActorsMoviesRepository actorsMoviesRepository;
    private ActorsMoviesMapper actorsMoviesMapper;
    private MoviesClient moviesClient;
    private ActorsClient actorsClient;

    @Override
    public Collection<ActorDto> getMovieActors(Long movieId) {
        logger.info("Retrieving actors for movieId: {}", movieId);
        Collection<ActorMovie> actorsMovies = actorsMoviesRepository.findAllByMovieId(movieId);
        Collection<ActorDto> result = new ArrayList<>();
        actorsMovies.forEach(am -> {
            ResponseEntity<ActorDto> responseMovie = actorsClient.getActor(am.getActorId(), false);
            if (responseMovie.hasBody()) {
                result.add(responseMovie.getBody());
            }
        });
        return result;
    }

    @Override
    public Collection<MovieDto> getActorMovies(Long actorId) {
        logger.info("Retrieving movies for actorId: {}", actorId);
        Collection<ActorMovie> actorsMovies = actorsMoviesRepository.findAllByActorId(actorId);
        Collection<MovieDto> result = new ArrayList<>();
        actorsMovies.forEach(am -> {
            ResponseEntity<MovieDto> responseMovie = moviesClient.getMovie(am.getMovieId(), false);
            if (responseMovie.hasBody()) {
                result.add(responseMovie.getBody());
            }
        });
        return result;
    }

    @Override
    public ActorMovieDto createActorMovie(ActorMovieDto actorMovieDto) {
        logger.info("Creating actor-movie with actorId: {} and movieId: {}", actorMovieDto.getActorId(), actorMovieDto.getMovieId());
        Optional<ActorMovie> existingActorMovieDto = actorsMoviesRepository.findByActorIdAndMovieId(actorMovieDto.getActorId(), actorMovieDto.getMovieId());
        if (existingActorMovieDto.isPresent()) {
            throw new ActorMovieAlreadyExistsException(String.format("There is already existing movie %s for actor %s.", actorMovieDto.getMovieId(), actorMovieDto.getActorId()));
        }
        ActorMovie actorMovie = actorsMoviesRepository.save(ActorMovie.builder().actorId(actorMovieDto.getActorId()).movieId(actorMovieDto.getMovieId()).build());
        return actorsMoviesMapper.mapToActorMovieDto(actorMovie, new ActorMovieDto());
    }

    @Override
    @Transactional
    public void deleteActorMovie(ActorMovieDto actorMovieDto) {
        logger.info("Deleting actor-movie with actorId: {} and movieId: {}", actorMovieDto.getActorId(), actorMovieDto.getMovieId());
        Optional<ActorMovie> existingActorMovieDto = actorsMoviesRepository.findByActorIdAndMovieId(actorMovieDto.getActorId(), actorMovieDto.getMovieId());
        existingActorMovieDto.ifPresent(actorMovie -> actorsMoviesRepository.delete(actorMovie));
    }

    @Override
    @Transactional
    public void deleteActorMovieByActorId(Long actorId) {
        logger.info("Delete actor-movie by actorId: {}", actorId);
        Collection<ActorMovie> existingActorMovieDto = actorsMoviesRepository.findAllByActorId(actorId);
        if (!CollectionUtils.isEmpty(existingActorMovieDto)) {
            actorsMoviesRepository.deleteAll(existingActorMovieDto);
        }
    }

    @Override
    @Transactional
    public void deleteActorMovieByMovieId(Long movieId) {
        logger.info("Delete actor-movie by movieId: {}", movieId);
        Collection<ActorMovie> existingActorMovieDto = actorsMoviesRepository.findAllByMovieId(movieId);
        if (CollectionUtils.isEmpty(existingActorMovieDto)) {
            actorsMoviesRepository.deleteAll(existingActorMovieDto);
        }
    }
}
