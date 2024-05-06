package com.astraser.code.challenge.actors.movies.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActorDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String birthDate;

    private String gender;

}
