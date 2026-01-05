package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
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
    public ResponseEntity<Void> save(@RequestBody AuthorDTO author) {
        Author authorEntity = author.mapToAuthor(author);
        authorService.save(authorEntity);

        // http://localhost:8080/authors/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        if(authorService.findById(uuid).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(uuid);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Void> updateById(@PathVariable String id, @RequestBody AuthorDTO authorDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(uuid);

        if(authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Author author = authorOptional.get();

        author.setName(authorDTO.name());
        author.setBirthDate(authorDTO.birthDate());
        author.setNationality(authorDTO.nationality());
        authorService.updateById(author);

        return  ResponseEntity.noContent().build();
    }
}
