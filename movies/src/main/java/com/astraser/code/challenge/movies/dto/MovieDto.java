package com.astraser.code.challenge.movies.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    private Collection<ActorDto> actors;

}
