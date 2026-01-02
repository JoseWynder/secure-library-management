package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest(){
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGenre.FICTION);
        book.setTitle("UFO");
        book.setPublicationDate(LocalDate.of(1980,1,2));

        Author author = authorRepository
                .findById(UUID.fromString("ce77d0f1-71e4-4583-88bb-cb6970864b37"))
                .orElse(null);

        book.setAuthor(author);
        bookRepository.save(book);
    }
}