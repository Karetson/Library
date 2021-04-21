package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.book.dto.BookRequest;
import pl.library.api.book.dto.CreateBookResponse;
import pl.library.api.book.dto.GetBookResponse;
import pl.library.domain.book.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    // creating a book
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public CreateBookResponse addBook(@Valid @RequestBody BookRequest bookRequest) {
        Book gainedBook = bookService.addBook(bookRequest.toBook());
        return new CreateBookResponse(gainedBook.getId());
    }

    // searching for all books by phrase
    @GetMapping("/search")
    public List<GetBookResponse> getAllBooksByPhrase(@RequestParam String phrase) {
        List<Book> gainedBooks = bookService.getAllBooksByPhrase(phrase);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    // searching for random books with set number earlier
    @GetMapping("/search/random")
    public List<GetBookResponse> getNBooksByRandom(@RequestParam Byte number) {
        List<Book> gainedBooks = bookService.getRandomBooksByNumber(number);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    // searching for book by ID and title
    @GetMapping("/{id}/{title}")
    public GetBookResponse getBookByIdAndTitle(@PathVariable Long id,
                                               @PathVariable String title) {
        Book gainedBook = bookService.getBookByIdAndTitle(id, title);
        return new GetBookResponse(gainedBook);
    }

    // searching for all books by genre
    @GetMapping("/search/genre")
    public List<GetBookResponse> getAllBooksByGenre(@Valid @RequestBody Genre genre) {
        List<Book> gainedBooks = bookService.getAllBooksByGenres(genre);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    // searching for all books by status
    @GetMapping("/search/{status}")
    public List<GetBookResponse> getAllBooksByStatus(@PathVariable Boolean status) {
        List<Book> gainedBooks = bookService.getAllBooksByStatus(status);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    // updating book
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public CreateBookResponse updateBook(@PathVariable Long id,
                                         @Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest.toBook());
        return new CreateBookResponse(updatedBook.getId());
    }

    // removing the book
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
