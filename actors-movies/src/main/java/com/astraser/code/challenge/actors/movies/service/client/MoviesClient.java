package com.astraser.code.challenge.actors.movies.service.client;

import com.astraser.code.challenge.actors.movies.dto.MovieDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movies")
public interface MoviesClient {

    @GetMapping("/api/v1/{id}")
    ResponseEntity<MovieDto> getMovie(@PathVariable @NotNull Long id, @RequestParam(defaultValue = "true") boolean includeActors);

}
