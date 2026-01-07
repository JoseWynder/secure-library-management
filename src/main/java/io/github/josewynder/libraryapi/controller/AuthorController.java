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
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author author = authorMapper.toEntity(dto);
        authorService.save(author);
        URI location = getHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(uuid);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Author author = authorOptional.get();
        authorService.deleteById(author);
        return ResponseEntity.noContent().build();
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
