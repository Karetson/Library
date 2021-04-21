package pl.library.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.genre.exception.GenreNotFoundException;
import pl.library.domain.genre.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public List<Book> getAllBooksByPhrase(String phrase) {
        return bookRepository.findAllByTitleOrAuthorLike(phrase).orElseThrow(()
                -> new BookNotFoundException("Book with phrase: '" + phrase + "' doesn't exists"));
    }

    public List<Book> getRandomBooksByNumber(Byte number) {
        if (number > 0) {
            return bookRepository.findRandomByNumber(number);
        } else {
            throw new ArithmeticException("Number must be greater than 0.");
        }
    }

    public Book getBookByIdAndTitle(Long id, String title) {
        return bookRepository.findByIdAndTitle(id, title).orElseThrow(()
                -> new BookNotFoundException("Book with id: '" + id + "' and title: '" + title + "' doesn't exists!"));
    }

    public List<Book> getAllBooksByGenres(Long id) {
        Genre gainedGenre = genreRepository.findById(id).orElseThrow(()
                -> new GenreNotFoundException("Genre with ID: '" + id + "' doesn't exists!"));

        return bookRepository.findAllByGenres(gainedGenre).orElseThrow(()
                -> new BookNotFoundException("Book with type: '" + gainedGenre + "' doesn't exists!"));
    }

    public List<Book> getAllBooksByStatus(Boolean status) {
        return bookRepository.findALlByStatus(status).orElseThrow(()
                -> new BookNotFoundException("There are no books"));
    }

    @Transactional
    public Book addBook(Book book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with title: '" + book.getTitle() + "' and author: '" + book.getAuthor() + "' already exists!");
        }

        if (book.getGenres().isEmpty()) {
            throw new NullPointerException();
        } else if (book.getCount() > 0) {
            book.setAvailable(book.getCount());
            book.setStatus(true);
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Number of books must be greater than 0!");
        }
    }

    @Transactional
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
            if (bookDetails.getAvailable() > 0) {
                bookDetails.setStatus(true);
            } else {
                bookDetails.setStatus(false);
            }

            return bookRepository.save(bookDetails);
        } else {
            throw new ArithmeticException("Count must be greater than : " + (bookDetails.getCount() - bookDetails.getAvailable() - 1));
        }
    }

    @Transactional
    public void deleteBook(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with ID: " + id + " doesn't exists!");
        }
    }
}