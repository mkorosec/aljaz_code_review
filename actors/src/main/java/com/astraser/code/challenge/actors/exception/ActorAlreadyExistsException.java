package com.astraser.code.challenge.actors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActorAlreadyExistsException extends RuntimeException {

    public ActorAlreadyExistsException(String message) {
        super(message);
    }

}
