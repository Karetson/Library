package pl.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.domain.model.book.Book;
import pl.library.exception.BookExistsException;
import pl.library.exception.BookNotFoundException;
import pl.library.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    @GetMapping("/book/id/{id}")
    public Book getBookById(@PathVariable long id) throws BookNotFoundException {
        return bookService.getById(id);
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() throws BookNotFoundException {
        return bookService.getAll();
    }

    @GetMapping("/book/title/{title}")
    public List<Book> getAllBooksByTitle(@PathVariable String title) throws BookNotFoundException {
        return bookService.getAllByTitle(title);
    }

    @GetMapping("/book/author/{author}")
    public List<Book> getAllBooksByAuthor(@PathVariable String author) throws BookNotFoundException {
        return bookService.getAllByAuthor(author);
    }

    @GetMapping("/book/type/{bookType}")
    public List<Book> getAllBooksByBookType(@PathVariable String bookType) throws BookNotFoundException {
        return bookService.getAllByBookType(bookType);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book postBook(@Valid @RequestBody Book book) throws BookExistsException {
        return bookService.add(book);
    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Book putBook(@PathVariable long id, @Valid @RequestBody Book book) throws BookExistsException, BookNotFoundException {
        return bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) throws BookNotFoundException {
        bookService.deleteById(id);
    }
}
