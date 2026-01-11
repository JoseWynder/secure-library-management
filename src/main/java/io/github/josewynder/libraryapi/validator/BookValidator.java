package io.github.josewynder.libraryapi.validator;

import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.exceptions.InvalidFieldException;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final LocalDate PRICE_REQUIRED_SINCE = LocalDate.of(2020, 1, 1);
    private final BookRepository bookRepository;

    public void validate(Book book) {
        if(existsAnotherBookWithIsbn(book)) {
            throw new DuplicateRegistrationException("A book with this ISBN already exists");
        }
        if(isMandatoryPriceMissing(book)) {
            throw new InvalidFieldException("price", "The price is mandatory for books published after 2020");
        }
    }

    private boolean existsAnotherBookWithIsbn(Book book) {
        String isbn = book.getIsbn();
        Optional<Book> byIsbn = bookRepository.findByIsbn(isbn);

        if(book.getId() == null) {
            return byIsbn.isPresent();
        }

        return byIsbn
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }

    private boolean isMandatoryPriceMissing(Book book) {
        return book.getPrice() == null &&
                book.getPublicationDate().isAfter(PRICE_REQUIRED_SINCE);
    }
}
