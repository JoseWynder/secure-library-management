package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Method

    List<Book> findByAuthor(Author author);

}
