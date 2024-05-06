package com.astraser.code.challenge.actors.movies.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieDto {

    private Long id;

    private String title;

    private String releaseDate;

    private String description;

    private Double rating;

}
