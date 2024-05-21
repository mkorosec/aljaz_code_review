package com.astraser.code.challenge.movies.controller;

import com.astraser.code.challenge.movies.dto.MovieDto;
import com.astraser.code.challenge.movies.service.MoviesService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class MoviesController {

    private MoviesService moviesService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable @NotNull Long id, @RequestParam(defaultValue = "true") boolean includeActors) {
        return ResponseEntity.status(HttpStatus.OK).body(moviesService.read(id, includeActors));
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movie) {
        MovieDto movieDto = moviesService.create(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable @NotNull Long id, @RequestBody MovieDto actor) {
        MovieDto actorDto = moviesService.update(id, actor);
        return ResponseEntity.status(HttpStatus.OK).body(actorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable @NotNull Long id) {
        moviesService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    private ResponseEntity<?> list(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return ResponseEntity.status(HttpStatus.OK).body(moviesService.listPaginated(page, size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(moviesService.list());
    }


}
