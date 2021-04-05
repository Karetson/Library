package pl.library.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.book.repository.BookService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooksByPhrase(String phrase) {
        return bookRepository.findAllByTitleOrAuthorLike(phrase).orElseThrow(()
                -> new BookNotFoundException("Book with phrase: '" + phrase + "' doesn't exists"));
    }

    @Override
    public List<Book> getRandomBooksByNumber(Byte number) {
        if (number > 0) {
            return bookRepository.findRandomByNumber(number);
        } else {
            throw new ArithmeticException("Number must be greater than 0.");
        }
    }

    @Override
    public Book getBookByIdAndTitle(Long id, String title) {
        return bookRepository.findByIdAndTitle(id, title).orElseThrow(()
                -> new BookNotFoundException("Book with id: '" + id + "' and title: '" + title + "' doesn't exists!"));
    }

    @Override
    public List<Book> getAllBooksByGenres(Genre genre) {
        return bookRepository.findAllByGenres(genre).orElseThrow(()
                -> new BookNotFoundException("Book with type: '" + genre + "' doesn't exists!"));
    }

    @Transactional
    @Override
    public Book addBook(Book book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with title: '" + book.getTitle() + "' and author: '" + book.getAuthor() + "' already exists!");
        }

        if (book.getGenres().isEmpty()) {
            throw new NullPointerException();
        } else if (book.getCount() > 0) {
            book.setAvailable(book.getCount());
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Number of books must be greater than 0!");
        }
    }

    @Transactional
    @Override
    public Book updateBook(Long id, Book book) {
        Book bookDetails = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));
        Integer diff = book.getCount() - bookDetails.getCount();

        if ((bookDetails.getAvailable() + diff) >= 0) {
            bookDetails.setTitle(book.getTitle());
            bookDetails.setAuthor(book.getAuthor());
            bookDetails.setPublisher(book.getPublisher());
            bookDetails.setGenres(book.getGenres());
            bookDetails.setDescription(book.getDescription());
            bookDetails.setCount(book.getCount());
            bookDetails.setAvailable(bookDetails.getAvailable() + diff);

            return bookRepository.save(bookDetails);
        } else {
            throw new ArithmeticException("Count must be greater than : " + (bookDetails.getCount() - bookDetails.getAvailable() - 1));
        }
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with ID: " + id + " doesn't exists!");
        }
    }
}