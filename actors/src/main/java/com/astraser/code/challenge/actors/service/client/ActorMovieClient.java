package com.astraser.code.challenge.actors.service.client;

import com.astraser.code.challenge.actors.dto.MovieDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@FeignClient(name="actor-movie")
public interface ActorMovieClient {

    @GetMapping("/api/v1/actors/{actorId}/movies")
    ResponseEntity<Collection<MovieDto>> getActorMovies(@NotNull @PathVariable Long actorId);

    @DeleteMapping("/api/v1/actors/{actorId}")
    ResponseEntity<Void> deleteActorMovieByActor(@NotNull @PathVariable Long actorId);


}
