package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("Maria");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        Author savedAuthor = authorRepository.save(author);
        System.out.println("Saved Author: " + savedAuthor);
    }

    @Test
    public void updateTest() {
        UUID id = UUID.fromString("223038b0-b504-418f-ac62-ee16f826c5bb");
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if(optionalAuthor.isPresent()) {
            Author savedAuthor = optionalAuthor.get();
            System.out.println("Saved author data: " + savedAuthor);

            savedAuthor.setBirthDate(LocalDate.of(1960, 1, 31));
            authorRepository.save(savedAuthor);
        }
    }

    @Test
    public void listAllTest() {
        List<Author> list = authorRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Author count: " + authorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        UUID id = UUID.fromString("223038b0-b504-418f-ac62-ee16f826c5bb");
        authorRepository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        UUID id = UUID.fromString("bbd50f23-f5e0-4ad4-ab4d-e25f7c2f7dd0");
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        optionalAuthor.ifPresent(savedAuthor -> authorRepository.delete(savedAuthor));
    }
}
