package pl.library.domain.borrow.repository;

import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;

import java.util.List;

public interface BorrowService {
    Borrow addition(Borrow borrow);
    List<Borrow> getAllBorrowsByStatus(BorrowStatus status);
    Borrow changeBorrowStatus(Long id, BorrowStatus status);
}
