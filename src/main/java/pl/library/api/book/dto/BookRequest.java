package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.Set;

@Value
public class BookRequest {
    String title;
    String author;
    String publisher;
    Set<Genre> genres;
    int count;
    String description;

    public Book toBook() {
        return Book.builder()
                .title(this.title)
                .author(this.author)
                .publisher(this.publisher)
                .genres(this.genres)
                .count(this.count)
                .description(this.description)
                .build();
    }
}
