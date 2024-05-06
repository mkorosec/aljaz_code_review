package com.astraser.code.challenge.actors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ActorNotFoundException extends RuntimeException {

    public ActorNotFoundException(String message) {
        super(message);
    }

}
