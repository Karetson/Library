package pl.library.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllByPhrase(String phrase) {
        if (bookRepository.searchAllByTitleOrAuthorLike(phrase).size() > 0) {
            return bookRepository.searchAllByTitleOrAuthorLike(phrase);
        } else {
            throw new BookNotFoundException("Book with phrase: '" + phrase + "' doesn't exists!");
        }
    }

    @Override
    public List<Book> getNBooksByRandom(Integer number) {
        if (number > 0 && number <= bookRepository.findAll().size()) {
            return bookRepository.searchNBooksByRandom(number);
        } else {
            throw new ArithmeticException("There are not enough books or number is not greater than 0.");
        }
    }

    @Override
    public Book getByIdAndTitle(Long id, String title) {
        return bookRepository.findByIdAndTitle(id, title).orElseThrow(()
                -> new BookNotFoundException("Book with id: '" + id + "' and title: '" + title + "' doesn't exists!"));
    }

//    @Override
//    public List<Book> getAllByGenres(String genre) {
//        if (bookRepository.findAllByGenres(genre).size() > 0) {
//            return bookRepository.findAllByGenres(genre);
//        } else {
//            throw new BookNotFoundException("Book with type: '" + genre + "' doesn't exists!");
//        }
//    }

    @Transactional
    @Override
    public Book addition(Book book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new BookExistsException("Book with title: '" + book.getTitle() + "' and author: '" + book.getAuthor() + "' already exists!");
        }
        if (book.getAvailable() > 0) {
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Amount must be greater than 0!");
        }
    }

    @Transactional
    @Override
    public Book addAvailable(Long id, Integer available) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));

        if (available > 0) {
            book.setAvailable(book.getAvailable() + available);
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Amount must be greater than 0!");
        }
    }

    @Transactional
    @Override
    public Book subtractAvailable(Long id, Integer amount) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book with ID: " + id + " doesn't exists!"));

        if (amount > 0 && amount <= book.getAvailable()) {
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setAvailable(book.getAvailable() - amount);
            book.setGenres(book.getGenres());
            return bookRepository.save(book);
        } else {
            throw new ArithmeticException("Amount must be greater than 0 and less than or equal " + book.getAvailable() + "!");
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