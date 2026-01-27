package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
import io.github.josewynder.libraryapi.controller.mappers.AuthorMapper;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors")
@Slf4j
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Author already registered")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        log.info("Saving a new author: {}", dto.name());

        Author author = authorMapper.toEntity(dto);
        authorService.save(author);
        URI location = getHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Get Details", description = "Returns the author's data by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author found"),
            @ApiResponse(responseCode = "422", description = "Author not found")
    })
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(uuid);

        return authorService
                .findById(uuid)
                .map(author -> {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // idempotent
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete", description = "Delete an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deleted successfully"),
            @ApiResponse(responseCode = "422", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "The author has a registered book")
    })
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        log.info("Deleting author with id: {}", id);
        UUID uuid = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(uuid);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Author author = authorOptional.get();
        authorService.delete(author);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Search", description = "Performs author searches using parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    public ResponseEntity<List<AuthorDTO>> searchByNameAndNationality(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nationality) {
        List<Author> authors = authorService.searchByExample(name, nationality);
        List<AuthorDTO> authorDTOS = authors
                .stream()
                .map(authorMapper::toDTO)
                .toList();
        return ResponseEntity.ok(authorDTOS);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update", description = "Update an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "409", description = "Author already registered")
    })
    public ResponseEntity<Void> updateById(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(uuid);

        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Author author = authorOptional.get();

        author.setName(authorDTO.name());
        author.setBirthDate(authorDTO.birthDate());
        author.setNationality(authorDTO.nationality());
        authorService.updateById(author);

        return ResponseEntity.noContent().build();
    }
}
