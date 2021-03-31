package pl.library.domain.book.repository;

import pl.library.adapters.mysql.model.book.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllByPhrase(String phrase);
    List<Book> getNumberRandomBooks(Byte number);
    Book getByIdAndTitle(Long id, String title);
//    List<Book> getAllByGenres(String genre);
    Book addition(Book book);
    Book updateBook(Long id, Book book);
    void bookDeletion(Long id);
}
