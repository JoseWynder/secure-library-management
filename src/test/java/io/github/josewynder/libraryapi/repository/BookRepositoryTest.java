package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setIsbn("94567-84874");
        book.setPrice(BigDecimal.valueOf(200));
        book.setGenre(BookGenre.MISTERY);
        book.setTitle("UFO");
        book.setPublicationDate(LocalDate.of(1975,1,2));

        Author author = authorRepository
                .findById(UUID.fromString("f5e1e06c-80ad-4b71-817c-ba953c29d95f"))
                .orElse(null);

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.FICTION);
        book.setTitle("Another Book");
        book.setPublicationDate(LocalDate.of(1980,1,2));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.FICTION);
        book.setTitle("Third Book");
        book.setPublicationDate(LocalDate.of(1980,1,2));

        Author author = new Author();
        author.setName("Jose");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1951, 1, 31));

        authorRepository.save(author);

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void updateBookAuthorTest() {
        UUID idBook = UUID.fromString("d854ea5b-26cc-4f2e-9fe8-e94f19b9232d");
        Book book = bookRepository.findById(idBook).orElseThrow();

        UUID idAuthor = UUID.fromString("081de40a-3ac6-4791-8947-dd18450ff5f5");
        Author newAuthor = authorRepository.findById(idAuthor).orElseThrow();
        book.setAuthor(newAuthor);
        bookRepository.save(book);

    }

    @Test
    void deleteTest() {
        UUID idBook = UUID.fromString("d854ea5b-26cc-4f2e-9fe8-e94f19b9232d");
        bookRepository.deleteById(idBook);
    }

    @Test
    void deleteCascadeTest() {
        UUID idBook = UUID.fromString("01b4e868-83ef-43e1-9bdc-9c46e48669aa");
        bookRepository.deleteById(idBook);
    }

    @Test
    @Transactional
    void findBookTest() {
        UUID uuid = UUID.fromString("3cc5b7a7-cd2f-4e5e-a5c3-758ef09f8b89");
        Book book = bookRepository.findById(uuid).orElseThrow();

        System.out.println("Book: ");
        System.out.println(book.getTitle());

//        System.out.println("Author: ");
//        System.out.println(book.getAuthor().getName());
    }

    @Test
    void searchByTitleTest() {
        List<Book> books = bookRepository.findByTitle("Third Book");
        books.forEach(System.out::println);
    }

    @Test
    void searchByIsbnTest() {
        List<Book> books = bookRepository.findByIsbn("23487-84874");
        books.forEach(System.out::println);
    }

    @Test
    void searchByTitleAndPriceTest() {
        List<Book> books = bookRepository
                .findByTitleAndPrice("Third Book", BigDecimal.valueOf(100L));
        books.forEach(System.out::println);
    }

    @Test
    void listBooksUsingJPQLQueryTest() {
        List<Book> books = bookRepository.listAllOrderByPriceAndTitle();
        books.forEach(System.out::println);
    }

    @Test
    void listAuthorsUsingJPQLQueryTest() {
        List<Author> authors = bookRepository.listAllAuthorsFromTheBooks();
        authors.forEach(System.out::println);
    }

    @Test
    void listDistinctBookTitlesUsingJPQLQueryTest() {
        List<String> titles = bookRepository.listDistinctBookTitles();
        titles.forEach(System.out::println);
    }

    @Test
    void listGenresBooksBrazilianAuthorsUsingJPQLQueryTest() {
        List<String> genres = bookRepository.listGenresBooksBrazilianAuthors();
        genres.forEach(System.out::println);
    }

    @Test
    void listGenreOrderByParameterTest() {
        List<Book> books = bookRepository.findByGenre(BookGenre.FICTION, Sort.by("title"));
        books.forEach(System.out::println);
    }

    @Test
    void listGenreWithPositionalParameterTest() {
        List<Book> books = bookRepository.findByGenreWithPositionalParameters(BigDecimal.valueOf(100L), BookGenre.FICTION);
        books.forEach(System.out::println);
    }

    @Test
    void deleteByGenreTest() {
        bookRepository.deleteByGenre(BookGenre.BIOGRAPHY);
    }

    @Test
    void updatePublicationDateByIdTest() {
        bookRepository.updatePublicationDate(UUID.fromString("0d040839-c2fc-4e2f-94ac-eaefa0c55ab8"), LocalDate.now());
    }
}