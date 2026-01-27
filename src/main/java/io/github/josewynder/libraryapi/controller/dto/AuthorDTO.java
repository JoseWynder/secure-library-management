package io.github.josewynder.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Author")
public record AuthorDTO(
        @Schema(name = "id")
        UUID id,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "field outside standard size")
        @Schema(name = "name")
        String name,
        @NotNull(message = "required field")
        @Past(message = "It cannot be a future date")
        @Schema(name = "birthDate")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "field outside standard size")
        @Schema(name = "nationality")
        String nationality) {
}
