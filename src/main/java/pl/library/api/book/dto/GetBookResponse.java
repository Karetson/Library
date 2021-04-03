package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.Set;

@Value
public class GetBookResponse {
    long id;
    String title;
    String author;
    String publisher;
    Set<Genre> genres;
    int available;
    String description;

    public GetBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.genres = book.getGenres();
        this.available = book.getAvailable();
        this.description = book.getDescription();
    }
}
