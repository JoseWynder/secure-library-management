package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.BookResponseDTO;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.controller.mappers.BookMapper;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody @Valid BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book savedBook = bookService.save(book);
        URI location = getHeaderLocation(book.getId());
        return ResponseEntity.created(location).body(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getDetails(
            @PathVariable String id) {
        return bookService
                .findById(UUID.fromString(id))
                .map( book -> {
                    BookResponseDTO bookResponseDTO = bookMapper.toDTO(book);
                    return ResponseEntity.ok(bookResponseDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return bookService
                .findById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
