package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.exception.BorrowException;
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
        Long userId = borrow.getUser().getId();
        Long bookId = borrow.getBook().getId();

        if (userRepository.findById(userId).isPresent() && bookRepository.findById(bookId).isPresent()) {
            Book book = bookRepository.findById(bookId).orElseThrow();

            book.setAvailable(book.getAvailable() - 1);
            bookRepository.save(book);

            return borrowRepository.save(borrow);
        } else {
            throw new BorrowException("User id: " + userId + " or book id: " + bookId + " doesn't exists!");
        }
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
    public Borrow changeBorrowStatus(Long id, BorrowStatus status) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow(()
                -> new BorrowNotFoundException("Borrow with " + id + " ID not found!"));

        borrow.setStatus(status);

        if (status.equals(BorrowStatus.DEVOTED)) {
            Book book = bookRepository.findById(borrow.getBook().getId()).orElseThrow();

            book.setAvailable(book.getAvailable() + 1);
            bookRepository.save(book);
        }

        return borrowRepository.save(borrow);
    }
}
