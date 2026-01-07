package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.controller.mappers.BookMapper;
import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        bookService.save(book);
        URI location = getHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }
}
