package io.github.josewynder.libraryapi.controller.mappers;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
import io.github.josewynder.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "nationality", target = "nationality")
    Author toEntity(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
