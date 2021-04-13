package pl.library.adapters.mysql.model.borrow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "borrows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    private LocalDateTime createdAt;
    private LocalDateTime expired;
    private LocalDateTime edited;
    @Builder.Default
    private BorrowStatus status = BorrowStatus.NOT_APPROVED;
}
