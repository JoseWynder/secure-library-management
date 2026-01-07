package io.github.josewynder.libraryapi.controller.dto;

import io.github.josewynder.libraryapi.controller.mappers.AuthorMapper;
import io.github.josewynder.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// Data Transfer Object
public record AuthorDTO(
        UUID id,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "field outside standard size")
        String name,
        @NotNull(message = "required field")
        @Past(message = "It cannot be a future date")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "field outside standard size")
        String nationality) {
}
