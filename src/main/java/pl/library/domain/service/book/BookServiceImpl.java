package pl.library.domain.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.service.book.exception.BookExistsException;
import pl.library.domain.service.book.exception.BookNotFoundException;
import pl.library.domain.service.book.repository.BookService;
import pl.library.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book getById(long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with " + id + " ID doesn't exists!"));
    }

    @Override
    public List<Book> getAll() throws BookNotFoundException {
        if (bookRepository.findAll().size() > 0) {
            return bookRepository.findAll();
        } else {
            throw new BookNotFoundException("There are no books in database.");
        }
    }
    @Override

    public List<Book> getAllByTitle(String title) throws BookNotFoundException {
        if (bookRepository.findAllByTitle(title).size() > 0) {
            return bookRepository.findAllByTitle(title);
        } else {
            throw new BookNotFoundException("Book with '" + title + "' title doesn't exists!");
        }
    }
    @Override

    public List<Book> getAllByAuthor(String author) throws BookNotFoundException {
        if (bookRepository.findAllByAuthor(author).size() > 0) {
            return bookRepository.findAllByAuthor(author);
        } else {
            throw new BookNotFoundException("Book with '" + author + "' title doesn't exists!");
        }
    }

    @Override
    public List<Book> getAllByBookType(String bookType) throws BookNotFoundException {
        if (bookRepository.findAllByBookType(bookType).size() > 0) {
            return bookRepository.findAllByBookType(bookType);
        } else {
            throw new BookNotFoundException("Book with '" + bookType + "' title doesn't exists!");
        }
    }

    @Transactional
    @Override
    public Book add(Book book) throws BookExistsException {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with '" + book.getTitle() + "' title and '" + book.getAuthor() + "' author already exists!");
        } else {
            return bookRepository.save(book);
        }
    }

    @Transactional
    @Override
    public Book update(long id, Book bookDetails) throws BookNotFoundException, BookExistsException {
        Book book = bookRepository.findById(id).
                orElseThrow(() -> new BookNotFoundException("Book with " + id + " ID doesn't exists!"));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setBookType(bookDetails.getBookType());
        book.setAmount(bookDetails.getAmount());
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with '" + book.getTitle() + "' title and '" + book.getAuthor() + "' author already exists!");
        } else {
            return bookRepository.save(book);
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) throws BookNotFoundException {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with " + id + " ID doesn't exists!");
        }
    }
}