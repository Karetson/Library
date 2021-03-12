package pl.library.domain.book.repository;

import pl.library.adapters.mysql.model.book.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);
    List<Book> getAll();
    List<Book> getAllByTitle(String title);
    List<Book> getAllByAuthor(String author);
    List<Book> getAllByBookType(String bookType);
    Book addition(Book book);
    Book addAmount(Long id, Integer amount);
    Book subtractAmount(Long id, Integer amount);
    void bookDeletion(Long id);
}
