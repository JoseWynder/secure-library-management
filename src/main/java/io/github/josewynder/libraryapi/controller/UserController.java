package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.UserRequestDTO;
import io.github.josewynder.libraryapi.controller.mappers.UserMapper;
import io.github.josewynder.libraryapi.model.User;
import io.github.josewynder.libraryapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserRequestDTO response) {
        User user = userMapper.toEntity(response);
        userService.save(user);
    }
}
