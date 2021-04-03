package pl.library.api.borrow.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.user.User;

@Value
public class CreateBorrowRequest {
    User user;
    Book book;

    public Borrow toBorrow() {
        return Borrow.builder()
                .user(this.user)
                .book(this.book)
                .build();
    }
}
