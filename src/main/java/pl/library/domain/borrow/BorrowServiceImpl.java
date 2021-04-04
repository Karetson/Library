package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.exception.BorrowExistsException;
import pl.library.domain.borrow.exception.BorrowNotFoundException;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.borrow.repository.BorrowService;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Borrow addition(Borrow borrow) throws UserNotFoundException {
        Long userId = borrow.getUser().getId();
        Long bookId = borrow.getBook().getId();
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new BookNotFoundException("There is no book with id: " + bookId));
        userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("There is no user with id: " + userId));

        if (borrowRepository.existsByUserAndBook(borrow.getUser(), borrow.getBook())) {
            throw new BorrowExistsException("Borrow with user id: " + userId + " and book id: " + bookId + " already exists!");
        } else if (book.getAvailable() > 0) {
            book.setAvailable(book.getAvailable() - 1);
            bookRepository.save(book);
            borrow.setCreatedAt(LocalDateTime.now());
            borrow.setExpired(borrow.getCreatedAt().plusDays(30));

            return borrowRepository.save(borrow);
        } else {
            throw new ArithmeticException("This book cannot be borrowed");
        }
    }

    @Override
    public List<Borrow> getAllBorrowsByStatus(BorrowStatus status) {
        return borrowRepository.findAllByStatus(status).orElseThrow(()
                -> new BorrowNotFoundException("Borrow with status: '" + status + "' not found!"));
    }

    @Override
    @Transactional
    public Borrow changeBorrowStatus(Long id, BorrowStatus status) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow(()
                -> new BorrowNotFoundException("Borrow with " + id + " ID not found!"));
        Long bookId = borrow.getBook().getId();
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new BookNotFoundException("Boook with " + bookId + " ID not found!"));

        if (status.equals(BorrowStatus.DEVOTED)) {
            borrow.setEdited(LocalDateTime.now());
            borrow.setStatus(status);
            book.setAvailable(book.getAvailable() + 1);
            bookRepository.save(book);
        } else if (status.equals(BorrowStatus.APPROVED)) {
            borrow.setEdited(LocalDateTime.now());
            borrow.setStatus(status);
        }

        return borrowRepository.save(borrow);
    }
}
