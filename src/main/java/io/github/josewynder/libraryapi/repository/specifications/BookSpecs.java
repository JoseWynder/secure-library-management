package io.github.josewynder.libraryapi.repository.specifications;

import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn){
        return (root, query, cb)
                -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title){
        // upper(book.title) like (%:param%)
        return (root, query, cb)
                -> cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(BookGenre genre){
        return (root, query, cb)
                -> cb.equal(root.get("genre"), genre);
    }
}
