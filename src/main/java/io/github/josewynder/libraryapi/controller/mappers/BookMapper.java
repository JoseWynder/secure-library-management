package io.github.josewynder.libraryapi.controller.mappers;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.BookResponseDTO;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(bookRequestDTO.authorId()).orElse(null) )")
    public abstract Book toEntity(BookRequestDTO bookRequestDTO);

    public abstract BookResponseDTO toDTO(Book book);
}
