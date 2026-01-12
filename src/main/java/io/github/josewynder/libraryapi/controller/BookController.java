package io.github.josewynder.libraryapi.controller;

import io.github.josewynder.libraryapi.controller.dto.BookRequestDTO;
import io.github.josewynder.libraryapi.controller.dto.BookResponseDTO;
import io.github.josewynder.libraryapi.controller.mappers.BookMapper;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import io.github.josewynder.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<Void> save(@RequestBody @Valid BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book savedBook = bookService.save(book);
        URI location = getHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
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
