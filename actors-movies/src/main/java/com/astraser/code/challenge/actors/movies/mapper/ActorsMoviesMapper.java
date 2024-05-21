package com.astraser.code.challenge.actors.movies.mapper;

import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.entity.ActorMovie;
import org.springframework.stereotype.Component;

@Component
public class ActorsMoviesMapper {

    public ActorMovieDto mapToActorMovieDto(ActorMovie actorMovie, ActorMovieDto actorMovieDto) {
        actorMovieDto.setId(actorMovie.getId());
        actorMovieDto.setActorId(actorMovie.getActorId());
        actorMovieDto.setMovieId(actorMovie.getMovieId());
        return actorMovieDto;
    }
}
