package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.book.BookServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/search/phrase")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByPhrase(@RequestParam String phrase)  {
        return bookService.getAllByPhrase(phrase);
    }

    @GetMapping("/search/random")
    public List<Book> getNBooksByRandom(@RequestParam Integer number) {
        return bookService.getNBooksByRandom(number);
    }

    @GetMapping("/{id}/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookByIdAndTitle(@PathVariable Long id, @PathVariable String title) {
        return bookService.getByIdAndTitle(id, title);
    }

    @GetMapping("/search/genre")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByGenres(@RequestParam String genre) {
        return bookService.getAllByGenres(genre);
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
