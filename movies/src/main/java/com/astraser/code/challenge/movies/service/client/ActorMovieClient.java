package com.astraser.code.challenge.movies.service.client;

import com.astraser.code.challenge.movies.dto.ActorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name="actor-movie")
public interface ActorMovieClient {

    @GetMapping("/api/v1/movies/{movieId}/actors")
    ResponseEntity<Collection<ActorDto>> getMovieActors(@NotNull @PathVariable Long movieId);

    @DeleteMapping("/api/v1/movies/{movieId}")
    ResponseEntity<Void> deleteActorMovieByMovie(@PathVariable @Valid Long movieId);

}
