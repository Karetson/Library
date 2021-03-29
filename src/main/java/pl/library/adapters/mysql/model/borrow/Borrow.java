package pl.library.adapters.mysql.model.borrow;

import lombok.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expired = createdAt.plusDays(30);
    @Builder.Default
    private BorrowStatus status = BorrowStatus.NOT_APPROVED; // only moderator/admin can set status
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
}
