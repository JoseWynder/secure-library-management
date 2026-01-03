package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * @see BookRepositoryTest
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Method
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // select * from book where author_id = ?
    List<Book> findByAuthor(Author author);

    // select * from book where title = ?
    List<Book> findByTitle(String title);

    // select * from book where isbn = ?
    List<Book> findByIsbn(String isbn);

    // select * from book where title = ? and price = ?
    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    // select * from book where title = ? or isbn = ?
    List<Book> findByTitleOrIsbnOrderByTitle(String title, String isbn);

    // select * from book where publication_date between ? and ?
    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate end);

    // JPQL - Reference entities and properties
    // select b.* from book as b order by b.price, b.title
    @Query("select b from Book as b order by b.price, b.title")
    List<Book> listAllOrderByPriceAndTitle();

    // select a.* from book as b join author as a on a.id = b.author_id
    @Query("select a from Book as b join b.author as a")
    List<Author> listAllAuthorsFromTheBooks();

    // select distinct b.title from book as b
    @Query("select distinct b.title from Book as b")
    List<String> listDistinctBookTitles();


    @Query("""
        select b.gender 
        from Book as b 
        join b.author as a 
        where a.nationality = 'Brazilian' 
        order by b.gender 
    """)
    List<String> listGenresBooksBrazilianAuthors();

}
