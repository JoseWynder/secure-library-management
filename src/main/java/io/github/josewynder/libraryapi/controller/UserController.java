package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.UserRequestDTO;
import io.github.josewynder.libraryapi.controller.mappers.UserMapper;
import io.github.josewynder.libraryapi.model.User;
import io.github.josewynder.libraryapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "User already registered")
    })
    public void save(@RequestBody @Valid UserRequestDTO response) {
        User user = userMapper.toEntity(response);
        userService.save(user);
    }
}
