package pl.library.domain.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.service.book.exception.BookExistsException;
import pl.library.domain.service.book.exception.BookNotFoundException;
import pl.library.domain.service.book.BookServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/book/?search={id}")
    public Book getSingleBookById(@PathVariable long id) {
        return bookService.getById(id);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() throws BookNotFoundException {
        return bookService.getAll();
    }

    @GetMapping("/book/?search={title}")
    public List<Book> getAllBooksByTitle(@PathVariable String title)  {
        return bookService.getAllByTitle(title);
    }

    @GetMapping("/book/?search={author}")
    public List<Book> getAllBooksByAuthor(@PathVariable String author) {
        return bookService.getAllByAuthor(author);
    }

    @GetMapping("/book/?search={bookType}")
    public List<Book> getAllBooksByBookType(@PathVariable String bookType) {
        return bookService.getAllByBookType(bookType);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.add(book);
    }

    @PutMapping("/book/?edit={id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Book updateBook(@PathVariable long id, @Valid @RequestBody Book book) throws BookExistsException {
        return bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }
}
