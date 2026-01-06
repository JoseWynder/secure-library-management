package io.github.josewynder.libraryapi.controller.dto;

import io.github.josewynder.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// Data Transfer Object
public record AuthorDTO(
        UUID id,
        @NotBlank(message = "required field")
        String name,
        @NotNull(message = "required field")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        String nationality) {

    public Author mapToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
        return author;
    }

    public static AuthorDTO mapToAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality());
    }

    public static List<AuthorDTO> mapToAuthorDTOList(List<Author> authors) {
        return authors.stream().map(AuthorDTO::mapToAuthorDTO).toList();
    }
}
