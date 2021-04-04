package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;

import java.util.Set;

@Value
public class LoginUserResponse {
    long id;
    String firstName;
    Set<Book> favoriteBooks;
    Set<Borrow> borrows;
}
