package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.model.Client;
import io.github.josewynder.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    public void save(@RequestBody Client client) {
        clientService.save(client);
    }
}
