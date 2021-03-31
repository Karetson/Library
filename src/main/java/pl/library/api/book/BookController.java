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

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooksByPhrase(@RequestParam String phrase)  {
        return bookService.getAllByPhrase(phrase);
    }

    @GetMapping("/search/random")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getNBooksByRandom(@RequestParam Byte number) {
        return bookService.getNumberRandomBooks(number);
    }

    @GetMapping("/{id}/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookByIdAndTitle(@PathVariable Long id, @PathVariable String title) {
        return bookService.getByIdAndTitle(id, title);
    }

//    @GetMapping("/search/genre")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Book> getAllBooksByGenres(@RequestParam String genre) {
//        return bookService.getAllByGenres(genre);
//    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addSingleOrManyBooks(@Valid @RequestBody Book book) {
        return bookService.addition(book);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Book subtractBookAvailable(@RequestParam Long id,
                                   @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long id) {
        bookService.bookDeletion(id);
    }
}
