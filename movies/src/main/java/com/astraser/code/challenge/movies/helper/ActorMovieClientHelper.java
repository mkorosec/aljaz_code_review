package com.astraser.code.challenge.movies.helper;

import com.astraser.code.challenge.movies.dto.ActorDto;
import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.service.client.ActorMovieClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
@Slf4j
public class ActorMovieClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(ActorMovieClientHelper.class);

    private ActorMovieClient actorMovieClient;

    public void getMovieActors(MovieDto movieDto) {
        try {
            ResponseEntity<Collection<ActorDto>> actorMoviesDtoResponseEntity = actorMovieClient.getMovieActors(movieDto.getId());

            if (actorMoviesDtoResponseEntity.hasBody()) {
                movieDto.setActors(actorMoviesDtoResponseEntity.getBody());
            }
        }
        catch(FeignException fe)
        {
            logger.error("Failed to fetch actors for movie {} with exception: {}",movieDto.getId(), fe.getMessage());
        }
    }

    public void deleteMovieActors(Long id)
    {
        try {
            actorMovieClient.deleteActorMovieByMovie(id);
        }
        catch(FeignException fe)
        {
            logger.error("Failed to delete actors for movie {} with exception: {}",id, fe.getMessage());
        }
    }
}
