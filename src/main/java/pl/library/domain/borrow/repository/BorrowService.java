package pl.library.domain.borrow.repository;

import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.domain.user.exception.UserNotFoundException;

import java.util.List;

public interface BorrowService {
    Borrow addition(Borrow borrow) throws UserNotFoundException;
    List<Borrow> getAllBorrowsByStatus(BorrowStatus status);
    Borrow changeBorrowStatus(Long id);
}
