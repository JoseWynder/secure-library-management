package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import io.github.josewynder.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    // book (title, ..., file_name) -> id.png
    @Transactional
    public void saveBookWithPhoto() {
        // save the book
        // bookRepository.save(book);

        // get the book id = book.getId();
        // UUID id = book.getId();

        // save book photo -> cloud bucket
        // bucketService.save(book.getPhoto(), id + ".png");

        // update the name of the saved file
        // book.setPhotoFileName(id + ".png");
    }

    @Transactional
    public void updateWithoutUpdating() {
        Book book = bookRepository
                .findById(UUID.fromString("0d8b8080-af66-4b53-a2bf-547a4f4a36a8"))
                .orElseThrow();

        book.setPublicationDate(LocalDate.now());
    }

    @Transactional
    public void execute(){
        // save the author
        Author author = new Author();
        author.setName("Pedro");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        authorRepository.save(author);

        // save the book
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.FICTION);
        book.setTitle("Pedro's Book");
        book.setPublicationDate(LocalDate.of(1980,1,2));

        book.setAuthor(author);
        bookRepository.save(book);

        if(author.getName().equals("Pedro")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
