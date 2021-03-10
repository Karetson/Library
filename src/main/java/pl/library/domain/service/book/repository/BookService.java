package pl.library.domain.service.book.repository;

import pl.library.adapters.mysql.model.book.Book;

import java.util.List;

public interface BookService {

    Book getById(long id);

    List<Book> getAll();

    List<Book> getAllByTitle(String title);

    List<Book> getAllByAuthor(String author);

    List<Book> getAllByBookType(String bookType);

    Book add(Book book);

    Book update(long id, Book book);

    void deleteById(long id);
}
