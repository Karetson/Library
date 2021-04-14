package pl.library.domain.borrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.adapters.mysql.model.user.User;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BorrowServiceImplTest {
    BorrowService systemUnderTest;
    static final Long BORROW_ID = 1L;

    @Mock
    BorrowRepository borrowRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BorrowService(borrowRepository, userRepository, bookRepository);
    }

    @Test
    void shouldReturnListOfBorrowsBasedOnStatus() {
        // given
        when(borrowRepository.findAllByStatus(any(BorrowStatus.class))).thenReturn(Optional.of(List.of(new Borrow())));
        BorrowStatus status = BorrowStatus.NOT_APPROVED;
        // when
        List<Borrow> allBorrowsByStatus = systemUnderTest.getAllBorrowsByStatus(status);
        // then
        assertThat(allBorrowsByStatus).containsExactly(new Borrow());
    }

    @Test
    void shouldCreateBorrow() throws UserNotFoundException {
        // given
        when(borrowRepository.save(any(Borrow.class))).thenReturn(new Borrow());
        Set<Genre> genres = new HashSet<>();
        User user = new User(BORROW_ID,
                "firstName",
                "lastName",
                "email",
                "password",
                null,
                null,
                null,
                null);
        Book book = new Book(BORROW_ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                5,
                true,
                "desc");
        BorrowStatus status = BorrowStatus.APPROVED;
        Borrow borrow = new Borrow(BORROW_ID, user, book, null, null, null, status);

        // when
        Borrow createdBorrow = systemUnderTest.addBorrow(borrow);

        // then
        assertThat(createdBorrow).isInstanceOf(Borrow.class);
    }
}