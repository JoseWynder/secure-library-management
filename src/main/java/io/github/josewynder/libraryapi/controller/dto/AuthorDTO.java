package io.github.josewynder.libraryapi.controller.dto;

import io.github.josewynder.libraryapi.model.Author;

import java.time.LocalDate;

// Data Transfer Object
public record AuthorDTO(
        String name,
        LocalDate birthDate,
        String nationality) {

    public Author mapToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
        return author;
    }
}
