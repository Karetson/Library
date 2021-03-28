package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.exception.BorrowNotFoundException;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.borrow.repository.BorrowService;
import pl.library.domain.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Borrow addition(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    @Override
    public List<Borrow> getAllBorrowsByStatus(BorrowStatus status) {
        if (borrowRepository.findAllByStatus(status).size() > 0) {
            return borrowRepository.findAllByStatus(status);
        } else {
            throw new BorrowNotFoundException("There are no borrows with status: " + status);
        }
    }

    @Override
    @Transactional
    public Borrow changeBorrowStatus(Long id) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow(()
                -> new BorrowNotFoundException("Borrow with " + id + " ID not found!"));
        borrow.setStatus(borrow.getStatus());

        return borrowRepository.save(borrow);
    }
}
