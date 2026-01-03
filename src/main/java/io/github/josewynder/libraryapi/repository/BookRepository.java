package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Method

    // select * from book where author_id = ?
    List<Book> findByAuthor(Author author);

    // select * from book where title = ?
    List<Book> findByTitle(String title);

    // select * from book where isbn = ?
    List<Book> findByIsbn(String isbn);

    // select * from book where title = ? and price = ?
    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    // select * from book where title = ? or isbn = ?
    List<Book> findByTitleOrIsbn(String title, String isbn);

}
