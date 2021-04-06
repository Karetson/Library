package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;

import java.util.Set;

@Value
public class AddFavoriteResponse {
    long id;
    Set<Book> favoriteBooks;
}
