package com.astraser.code.challenge.actors.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class MovieDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDate releaseDate;

    @NotBlank
    private String description;

    @NotNull
    private Double rating;

    @JsonIgnore
    private Collection<ActorDto> actors;

}
