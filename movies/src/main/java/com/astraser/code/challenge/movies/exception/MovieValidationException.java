package com.astraser.code.challenge.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MovieValidationException extends RuntimeException {

    public MovieValidationException(String message) {
        super(message);
    }

}
