package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.exceptions.OperationNotPermittedException;
import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.User;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import io.github.josewynder.libraryapi.repository.BookRepository;
import io.github.josewynder.libraryapi.security.SecurityService;
import io.github.josewynder.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidator authorValidator;
    private final SecurityService securityService;

    public Author save(Author author) {
        authorValidator.validate(author);
        User user = securityService.getLoggedUser();
        author.setUser(user);
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
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

    public List<Author> searchByExample(String name, String nationality) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "birthDate", "registrationDate")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);
        return authorRepository.findAll(authorExample);
    }

    public void updateById(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("To update, the author must already be saved in the database!");
        }

        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public boolean haveABook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
