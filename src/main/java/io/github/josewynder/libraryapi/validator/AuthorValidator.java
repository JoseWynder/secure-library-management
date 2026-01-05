package io.github.josewynder.libraryapi.validator;

import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author) {
        if(thisAuthorExists(author)) {
           throw new DuplicateRegistrationException("Author already exists");
        }
    }

    private boolean thisAuthorExists(Author author) {
        Optional<Author> authorFound = authorRepository
                .findByNameAndBirthDateAndNationality(
                        author.getName(),
                        author.getBirthDate(),
                        author.getNationality());

        if (author.getId() == null) {
            return authorFound.isPresent();
        }
        return authorFound.isPresent() && !author.getId().equals(authorFound.get().getId());
    }
}
