package pl.library.domain.book.repository;

import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;

public interface BookService {
    List<Book> getAllBooksByPhrase(String phrase);
    List<Book> getRandomBooksByNumber(Byte number);
    Book getBookByIdAndTitle(Long id, String title);
    List<Book> getAllBooksByGenres(Genre genre);
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
}
