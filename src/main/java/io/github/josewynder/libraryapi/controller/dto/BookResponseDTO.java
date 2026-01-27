package io.github.josewynder.libraryapi.controller.dto;

import io.github.josewynder.libraryapi.model.BookGenre;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "BookResponse")
public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGenre genre,
        BigDecimal price,
        AuthorDTO author) {

}
