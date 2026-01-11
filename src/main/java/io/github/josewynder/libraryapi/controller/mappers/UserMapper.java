package io.github.josewynder.libraryapi.controller.mappers;

import io.github.josewynder.libraryapi.controller.dto.UserRequestDTO;
import io.github.josewynder.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
}
