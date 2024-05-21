package com.astraser.code.challenge.actors.controller;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.service.ActorsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ActorsController {

    private ActorsService actorsService;

    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<ActorDto> get(@PathVariable @NotNull Long id, @RequestParam(defaultValue = "true") boolean movies) {
        return ResponseEntity.status(HttpStatus.OK).body(actorsService.read(id, movies));
    }

    @PostMapping
    private ResponseEntity<ActorDto> create(@RequestBody @Valid ActorDto actor) {
        ActorDto actorDto = actorsService.create(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid ActorDto actor) {
        ActorDto actorDto = actorsService.update(id, actor);
        return ResponseEntity.status(HttpStatus.OK).body(actorDto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        actorsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    private ResponseEntity<?> list(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return ResponseEntity.status(HttpStatus.OK).body(actorsService.listPaginated(page, size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(actorsService.list());
    }

}
