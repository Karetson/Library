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
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (!(userRepository.findById(userId).isPresent() && bookRepository.findById(bookId).isPresent())) {
            throw new BorrowException("User id: " + userId + " or book id: " + bookId + " doesn't exists!");
        } else if (book.getAvailable() > 0) {
            book.setAvailable(book.getAvailable() - 1);
            bookRepository.save(book);

            return borrowRepository.save(borrow);
        } else {
            throw new ArithmeticException("This book cannot be borrowed");
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
        Book book = bookRepository.findById(borrow.getBook().getId()).orElseThrow();

        if (status.equals(BorrowStatus.DEVOTED)) {
            borrow.setStatus(status);
            book.setAvailable(book.getAvailable() + 1);
            bookRepository.save(book);
        } else if (status.equals(BorrowStatus.APPROVED)) {
            borrow.setStatus(status);
        }

        return borrowRepository.save(borrow);
    }
}
