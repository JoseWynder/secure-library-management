package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import io.github.josewynder.libraryapi.repository.BookRepository;
import io.github.josewynder.libraryapi.repository.specifications.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.josewynder.libraryapi.repository.specifications.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> search(String isbn, String title, String authorName,
                             BookGenre genre, Integer publicationYear) {

        // select * from book where isbn = :isbn and author_name...

//        Specification<Book> specs = Specification
//                .where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title))
//                .and(BookSpecs.genreEqual(genre))
//                ;

        Specification<Book> specification = Specification
                        .anyOf((
                                root,
                                query,
                                cb)
                                -> cb.conjunction());
        if(isbn != null){
            specification = specification.and(isbnEqual(isbn));
        }

        if(title != null){
            specification = specification.and(titleLike(title));
        }

        if(genre != null){
            specification = specification.and(genreEqual(genre));
        }

        if(publicationYear != null){
            specification = specification.and(yearEqual(publicationYear));
        }

        if(authorName != null){
            specification = specification.and(authorNameLike(authorName));
        }

        return bookRepository.findAll(specification);
    }

    public void update(Book book) {
        if(book.getId() == null) {
            throw new IllegalArgumentException(
                    "To update, the book must already be saved in the database!");
        }

        bookRepository.save(book);
    }
}
