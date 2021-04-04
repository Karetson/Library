package pl.library.api.borrow.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.adapters.mysql.model.user.User;

import java.time.LocalDateTime;

@Value
public class GetBorrowResponse {
    LocalDateTime createdAt;
    LocalDateTime expired;
    BorrowStatus status;
    User user;
    Book book;

    public GetBorrowResponse(Borrow borrow) {
        this.createdAt = borrow.getCreatedAt();
        this.expired = borrow.getExpired();
        this.status = borrow.getStatus();
        this.user = borrow.getUser();
        this.book = borrow.getBook();
    }
}
