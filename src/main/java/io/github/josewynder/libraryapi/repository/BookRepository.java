package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.model.Book;
import io.github.josewynder.libraryapi.model.BookGenre;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        select b.genre 
        from Book as b 
        join b.author as a 
        where a.nationality = 'Brazilian' 
        order by b.genre 
    """)
    List<String> listGenresBooksBrazilianAuthors();

    // Named parameters
    @Query("select b from Book as b where b.genre = :genre")
    List<Book> findByGenre(@Param("genre") BookGenre genre, Sort sort);

    @Query("select b from Book as b where b.genre = ?2 and b.price > ?1")
    List<Book> findByGenreWithPositionalParameters(BigDecimal price, BookGenre genre);

    @Modifying
    @Transactional
    @Query("delete from Book where genre = ?1")
    void deleteByGenre(BookGenre genre);

    @Modifying
    @Transactional
    @Query("update Book set publicationDate = ?2 where id = ?1")
    void updatePublicationDate(UUID id, LocalDate publicationDate);
}
