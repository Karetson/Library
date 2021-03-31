package pl.library.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.library.adapters.mysql.model.book.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM books WHERE title LIKE %:phrase% or author LIKE %:phrase% ORDER BY title ASC", nativeQuery = true)
    Optional<List<Book>> findAllByTitleOrAuthorLike(@Param("phrase") String phrase);
    @Query(value = "SELECT * FROM books ORDER BY RAND() LIMIT :number", nativeQuery = true)
    List<Book> findRandomByNumber(@Param("number") Byte number);
    Optional<Book> findByIdAndTitle(Long id, String title);
//    List<Book> findAllByGenres(String genre);
    Boolean existsByTitleAndAuthor(String title, String author);
}