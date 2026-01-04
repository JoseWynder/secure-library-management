package io.github.josewynder.libraryapi.controller.dto;

import java.time.LocalDate;

// Data Transfer Object
public record AuthorDTO(
        String name,
        LocalDate birthDate,
        String nationality) {
}
