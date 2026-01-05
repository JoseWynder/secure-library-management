package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.controller.dto.AuthorDTO;
import io.github.josewynder.libraryapi.exceptions.OperationNotPermittedException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import io.github.josewynder.libraryapi.repository.BookRepository;
import io.github.josewynder.libraryapi.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidator authorValidator;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, AuthorValidator authorValidator) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorValidator = authorValidator;
    }

    public Author save(Author author) {
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteById(Author author) {
        if(haveABook(author)) {
            throw new OperationNotPermittedException(
                    "You are not allowed to delete an author who has books registered!");
        }
        authorRepository.delete(author);
    }

    public List<Author> searchByNameAndNationality(String name, String nationality) {
        if(name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        if (name != null) {
            return authorRepository.findByName(name);
        }
        if(nationality != null) {
            return authorRepository.findByNationality(nationality);
        }

        return authorRepository.findAll();
    }

    public void updateById(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Author id must not be null");
        }

        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public boolean haveABook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
