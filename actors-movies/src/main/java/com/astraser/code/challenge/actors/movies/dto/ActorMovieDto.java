package com.astraser.code.challenge.actors.movies.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActorMovieDto {

    private Long id;

    @NotNull
    private Long actorId;

    @NotNull
    private Long movieId;
}
