package com.astraser.code.challenge.actors.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ActorMovieNotFoundException extends RuntimeException {

    public ActorMovieNotFoundException(String message) {
        super(message);
    }

}
