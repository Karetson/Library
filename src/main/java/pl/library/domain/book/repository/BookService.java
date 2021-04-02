package pl.library.domain.book.repository;

import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;

public interface BookService {
    List<Book> getAllByPhrase(String phrase);
    List<Book> getNumberRandomBooks(Byte number);
    Book getByIdAndTitle(Long id, String title);
    List<Book> getAllByGenres(Genre genre);
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    void bookDeletion(Long id);
}
