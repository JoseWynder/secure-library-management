package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.BookResponseDTO;
import io.github.josewynder.libraryapi.controller.mappers.BookMapper;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import io.github.josewynder.libraryapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Save", description = "Register a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Book already registered")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book savedBook = bookService.save(book);
        URI location = getHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Get Details", description = "Retrieve book details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
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
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Delete", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return bookService
                .findById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Search", description = "Search books by ISBN, title, author, genre or year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponseDTO>> search(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "genre", required = false)
            BookGenre genre,
            @RequestParam(value = "publication-year", required = false)
            Integer publicationYear,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize
    ) {
        Page<Book> booksPage = bookService
                .search(isbn, title, authorName, genre, publicationYear, page, pageSize);

        Page<BookResponseDTO> dtos = booksPage.map(bookMapper::toDTO);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Update", description = "Update a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Book already registered")
    })
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid BookRequestDTO dto) {
        return bookService
                .findById(UUID.fromString(id))
                .map(book -> {
                    Book newData = bookMapper.toEntity(dto);
                    book.setPublicationDate(newData.getPublicationDate());
                    book.setIsbn(newData.getIsbn());
                    book.setPrice(newData.getPrice());
                    book.setGenre(newData.getGenre());
                    book.setTitle(newData.getTitle());
                    book.setAuthor(newData.getAuthor());
                    bookService.update(book);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
