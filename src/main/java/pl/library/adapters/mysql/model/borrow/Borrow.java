package pl.library.adapters.mysql.model.borrow;

import lombok.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "borrows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt; // when status = true then set createdAt to current date
    private Date expired;   // when status = true then set expired to (createdAt + 30 days)
    @Builder.Default
    private BorrowStatus status = BorrowStatus.NOT_APPROVED; // only moderator/admin can set status

    private User userId;
    private Book bookId;
}
