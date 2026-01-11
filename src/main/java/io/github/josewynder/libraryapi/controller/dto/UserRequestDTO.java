package io.github.josewynder.libraryapi.controller.dto;

import java.util.List;

public record UserRequestDTO(
        String login,
        String password,
        List<String> roles
) {

}
