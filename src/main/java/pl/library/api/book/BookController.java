package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.book.BookServiceImpl;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Book getSingleBookById(@RequestParam Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/search/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByTitle(@PathVariable String title)  {
        return bookService.getAllByTitle(title);
    }

    @GetMapping("/search/author/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByAuthor(@PathVariable String author) {
        return bookService.getAllByAuthor(author);
    }

    @GetMapping("/search/type/{bookType}")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByBookType(@PathVariable String bookType) {
        return bookService.getAllByBookType(bookType);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addSingleOrManyBooks(@Valid @RequestBody Book book) {
        return bookService.addition(book);
    }

    @PutMapping("/edit/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBookAmount(@RequestParam Long id,
                               @RequestParam Integer amount) {
        return bookService.addAmount(id, amount);
    }

    @PutMapping("/edit/subtract")
    @ResponseStatus(HttpStatus.CREATED)
    public Book subtractBookAmount(@RequestParam Long id,
                              @RequestParam Integer amount) {
        return bookService.subtractAmount(id, amount);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long id) {
        bookService.bookDeletion(id);
    }
}
