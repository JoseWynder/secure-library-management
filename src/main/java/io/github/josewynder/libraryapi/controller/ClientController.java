package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.model.Client;
import io.github.josewynder.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Client already registered")
    })
    public void save(@RequestBody Client client) {
        log.info("Registering a new Client: {} with scope: {} ",
                client.getClientId(), client.getScope());
        clientService.save(client);
    }
}
