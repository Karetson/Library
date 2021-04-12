package pl.library.api.borrow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.api.book.dto.GetBookResponse;

import java.time.LocalDateTime;

@Value
public class GetBorrowResponse {
    long id;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime expired;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
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
