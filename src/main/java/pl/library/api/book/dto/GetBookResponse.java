package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GetGenreResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class GetBookResponse {
    long id;
    String title;
    String author;
    String publisher;
    Set<GetGenreResponse> genres;
    int available;
    Boolean status;
    String description;

    public GetBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.genres = book.getGenres().stream().map(GetGenreResponse::new).collect(Collectors.toSet());
        this.available = book.getAvailable();
        this.status = book.getStatus();
        this.description = book.getDescription();
    }
}
