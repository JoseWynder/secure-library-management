package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

}
