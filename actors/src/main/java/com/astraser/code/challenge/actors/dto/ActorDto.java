package com.astraser.code.challenge.actors.dto;

import com.astraser.code.challenge.actors.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class ActorDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    private Collection<MovieDto> movies;

}
