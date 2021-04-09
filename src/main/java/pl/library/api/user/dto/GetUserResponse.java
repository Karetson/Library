package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.api.borrow.dto.GetBorrowResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class GetUserResponse {
    long id;
    String firstName;
    String lastName;
    String email;
    Set<Book> favoriteBooks;
    Set<GetBorrowResponse> borrows;
    LocalDateTime createdAt;
}
