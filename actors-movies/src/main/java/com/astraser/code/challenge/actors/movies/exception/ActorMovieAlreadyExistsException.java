package com.astraser.code.challenge.actors.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActorMovieAlreadyExistsException extends RuntimeException {

    public ActorMovieAlreadyExistsException(String message) {
        super(message);
    }

}
