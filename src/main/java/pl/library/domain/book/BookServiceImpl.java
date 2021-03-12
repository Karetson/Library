package pl.library.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookService;
import pl.library.domain.book.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));
    }

    @Override
    public List<Book> getAll() {
        if (bookRepository.findAll().size() > 0) {
            return bookRepository.findAll();
        } else {
            throw new BookNotFoundException("There are no books in database.");
        }
    }

    @Override
    public List<Book> getAllByTitle(String title) {
        if (bookRepository.findAllByTitle(title).size() > 0) {
            return bookRepository.findAllByTitle(title);
        } else {
            throw new BookNotFoundException("Book with title: '" + title + "' doesn't exists!");
        }
    }

    @Override
    public List<Book> getAllByAuthor(String author) {
        if (bookRepository.findAllByAuthor(author).size() > 0) {
            return bookRepository.findAllByAuthor(author);
        } else {
            throw new BookNotFoundException("Book with author: '" + author + "' doesn't exists!");
        }
    }

    @Override
    public List<Book> getAllByBookType(String bookType) {
        if (bookRepository.findAllByBookType(bookType).size() > 0) {
            return bookRepository.findAllByBookType(bookType);
        } else {
            throw new BookNotFoundException("Book with type: '" + bookType + "' doesn't exists!");
        }
    }

    @Transactional
    @Override
    public Book addition(Book book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with title: '" + book.getTitle() + "' and author: '" + book.getAuthor() + "' already exists!");
        }
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book addAmount(Long id, Integer amount) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));

        if (amount > 0) {
            book.setAmount(book.getAmount() + amount);
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Amount must be greater than 0!");
        }
    }

    @Transactional
    @Override
    public Book subtractAmount(Long id, Integer amount) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));

        if (amount > 0 && amount <= book.getAmount()) {
            book.setAmount(book.getAmount() - amount);
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Amount must be greater than 0 and less than or equal " + book.getAmount() + "!");
        }
    }

    @Transactional
    @Override
    public void bookDeletion(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with ID: " + id + " doesn't exists!");
        }
    }
}