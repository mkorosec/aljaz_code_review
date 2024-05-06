package com.astraser.code.challenge.actors.movies.controller;

import com.astraser.code.challenge.actors.movies.dto.ActorDto;
import com.astraser.code.challenge.actors.movies.dto.ActorMovieDto;
import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import com.astraser.code.challenge.actors.movies.service.ActorsMoviesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ActorsMoviesController {

    private ActorsMoviesService actorsMoviesService;

    @GetMapping("/actors/{actorId}/movies")
    private ResponseEntity<Collection<MovieDto>> getActorMovies(@NotNull @PathVariable Long actorId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(actorsMoviesService.getActorMovies(actorId));

    }

    @GetMapping("/movies/{movieId}/actors")
    private ResponseEntity<Collection<ActorDto>> getMovieActors(@NotNull @PathVariable Long movieId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(actorsMoviesService.getMovieActors(movieId));

    }

    @DeleteMapping("/movies/{movieId}")
    private ResponseEntity<Void> deleteActorMovieByMovie(@NotNull @PathVariable Long movieId)
    {
        actorsMoviesService.deleteActorMovieByMovieId(movieId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("/actors/{actorId}")
    private ResponseEntity<Void> deleteActorMovieByActor(@NotNull @PathVariable Long actorId)
    {
        actorsMoviesService.deleteActorMovieByActorId(actorId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping()
    private ResponseEntity<ActorMovieDto> createActorMovie(@RequestBody @Valid ActorMovieDto actorMovieDto)
    {
        ActorMovieDto actorMovie = actorsMoviesService.createActorMovie(actorMovieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(actorMovie);

    }

    @DeleteMapping()
    private ResponseEntity<Void> deleteActorMovie(@RequestBody @Valid ActorMovieDto actorMovieDto)
    {
        actorsMoviesService.deleteActorMovie(actorMovieDto);
        return ResponseEntity.status(HttpStatus.OK).build();

    }


}
