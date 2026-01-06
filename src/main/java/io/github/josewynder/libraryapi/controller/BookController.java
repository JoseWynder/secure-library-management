package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.ErrorField;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.repository.BookRepository;
import io.github.josewynder.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRequestDTO book) {
        try {
            // map dto to entity
            // send the entity to service to validate and saving in the database
            // create a url to access the book data
            // return code created with header location
            return ResponseEntity.ok(book);
        } catch (DuplicateRegistrationException e) {
            ResponseError errorDTO = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
