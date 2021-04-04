package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.api.book.dto.GetBookResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class GetUserResponse {
    String firstName;
    String lastName;
    String email;
    Set<Book> favoriteBooks;
    Set<Borrow> borrows;
    LocalDateTime createdAt;
}
