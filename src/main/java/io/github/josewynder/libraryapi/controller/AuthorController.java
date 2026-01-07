package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.controller.mappers.AuthorMapper;
import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.exceptions.OperationNotPermittedException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
// http://localhost:8080/authors
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO dto) {
        try {
            Author author = authorMapper.toEntity(dto);
            authorService.save(author);

            // http://localhost:8080/authors/{id}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRegistrationException dre) {
            var errorDTO = ResponseError.conflict(dre.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(uuid);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Author author = authorOptional.get();
            authorService.deleteById(author);
            return ResponseEntity.noContent().build();
        } catch (OperationNotPermittedException e) {
            var responseError =  ResponseError.defaultAnswer(e.getMessage());
            return ResponseEntity.status(responseError.status()).body(responseError);
        }
    }

    @GetMapping
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
    public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO) {
        try {
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
        } catch (DuplicateRegistrationException dre) {
            var errorDTO = ResponseError.conflict(dre.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
