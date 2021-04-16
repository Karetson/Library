package pl.library.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import pl.library.adapters.mysql.model.role.Role;
import pl.library.api.book.dto.GetBookResponse;
import pl.library.api.borrow.dto.GetBorrowResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class GetUserResponse {
    long id;
    String firstName;
    String lastName;
    String email;
    Set<Role> role;
    Set<GetBookResponse> favoriteBooks;
    Set<GetBorrowResponse> borrows;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime createdAt;
}
