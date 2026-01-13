package io.github.josewynder.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserRequestDTO(
        @NotBlank(message = "Mandatory field")
        String login,
        @NotBlank(message = "Mandatory field")
        String password,
        @NotBlank(message = "Mandatory field")
        @Email(message = "invalid")
        String email,
        List<String> roles
) {

}
