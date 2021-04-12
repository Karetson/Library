package pl.library.api.borrow.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.api.book.dto.GetBookResponse;

import java.time.LocalDateTime;

@Value
public class GetBorrowResponse {
    long id;
    LocalDateTime createdAt;
    LocalDateTime expired;
    LocalDateTime edited;
    BorrowStatus status;
    GetBookResponse book;

    public GetBorrowResponse(Borrow borrow) {
        this.id = borrow.getId();
        this.createdAt = borrow.getCreatedAt();
        this.expired = borrow.getExpired();
        this.edited = borrow.getEdited();
        this.status = borrow.getStatus();
        this.book = new GetBookResponse(borrow.getBook());
    }
}
