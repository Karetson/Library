package pl.library.domain.borrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.user.User;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Boolean existsByUserAndBook(User user, Book book);
}
