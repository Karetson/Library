package pl.library.domain.borrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findAllByStatus(BorrowStatus status);
}
