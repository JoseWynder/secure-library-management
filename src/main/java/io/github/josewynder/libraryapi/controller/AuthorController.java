package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.exceptions.OperationNotPermittedException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
// http://localhost:8080/authors
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AuthorDTO author) {
        try {
            Author authorEntity = author.mapToAuthor(author);
            authorService.save(authorEntity);

            // http://localhost:8080/authors/{id}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
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
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO authorDTO = AuthorDTO.mapToAuthorDTO(author);
            return ResponseEntity.ok(authorDTO);
        }
        return ResponseEntity.notFound().build();
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
        } catch (OperationNotPermittedException onpe) {
            var responseError =  ResponseError.defaultAnswer(onpe.getMessage());
            return ResponseEntity.status(responseError.status()).body(responseError);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchByNameAndNationality(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nationality) {
        List<Author> authors = authorService.searchByNameAndNationality(name, nationality);
        List<AuthorDTO> authorDTOS = AuthorDTO.mapToAuthorDTOList(authors);
        return ResponseEntity.ok(authorDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody AuthorDTO authorDTO) {
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
