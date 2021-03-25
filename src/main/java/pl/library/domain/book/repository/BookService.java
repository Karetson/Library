package pl.library.domain.book.repository;

import pl.library.adapters.mysql.model.book.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllByPhrase(String phrase);
    List<Book> getNBooksByRandom(Integer number);
    Book getByIdAndTitle(Long id, String title);
    //    List<Book> getAllByGenres(String genre);
    Book addition(Book book);
    Book addAvailables(Long id, Integer amount);
    Book subtractAvailables(Long id, Integer amount);
    void bookDeletion(Long id);
}
