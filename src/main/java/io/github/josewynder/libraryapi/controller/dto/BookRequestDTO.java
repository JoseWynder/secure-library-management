package io.github.josewynder.libraryapi.controller.dto;

import io.github.josewynder.libraryapi.model.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequestDTO(
        @ISBN
        @NotBlank(message = "required field")
        String isbn,
        @NotBlank(message = "required field")
        String title,
        @NotNull(message = "required field")
        @Past(message = "It cannot be a future date")
        LocalDate publicationDate,
        BookGenre genre,
        BigDecimal price,
        @NotNull(message = "required field")
        UUID authorId) {

}
