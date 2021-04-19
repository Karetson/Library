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
import pl.library.adapters.mysql.model.role.Role;
import pl.library.adapters.mysql.model.user.User;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.exception.BorrowExistsException;
import pl.library.domain.borrow.exception.BorrowNotFoundException;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BorrowServiceTest {
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

    BorrowService systemUnderTest;
    Set<Role> roles = new HashSet<>();

    static final Long ID = 1L;
    User user = new User(ID,
            "firstName",
            "lastName",
            "email",
            "password",
            roles,
            null,
            null,
            null,
            null);

    @Test
    void shouldReturnAllBorrowsBasedOnStatus() {
        // given
        when(borrowRepository.findAllByStatus(any(BorrowStatus.class))).thenReturn(Optional.of(List.of(new Borrow())));
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        // when
        List<Borrow> allBorrowsByStatus = systemUnderTest.getAllBorrowsByStatus(status);

        // then
        assertThat(allBorrowsByStatus).containsExactly(new Borrow());
    }

    @Test
    void shouldNotReturnAllBorrowsBasedOnStatusWhenBorrowIsNotFound() {
        // given
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBorrowsByStatus(status)).isInstanceOf(BorrowNotFoundException.class);
    }

    @Test
    void shouldCreateBorrow() throws UserNotFoundException {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                5,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);
        // when
        Borrow createdBorrow = systemUnderTest.addBorrow(borrow);

        // then
        assertThat(createdBorrow).isInstanceOf(Borrow.class);
    }

    @Test
    void shouldNotCreateBorrowWhenBookIsNotFound() {
        // given
        User user = new User();
        Book book = new Book();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldNotCreateBorrowWhenUserIsNotFound() {
        // given
        BorrowStatus status = BorrowStatus.NOT_APPROVED;
        Set<Genre> genres = new HashSet<>();
        User user = new User();
        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                5,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldNotCreateBorrowWhenBorrowAlreadyExists() {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                5,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(borrowRepository.existsByUserAndBook(any(User.class), any(Book.class))).thenReturn(true);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(BorrowExistsException.class);
    }

    @Test
    void shouldNotCreateBorrowWhenBookAvailableLTE0() {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                0,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldChangeBorrowStatusWhenStatusChangedToApproved() {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                0,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        // when
        Borrow changedBorrowStatus = systemUnderTest.changeBorrowStatus(ID, BorrowStatus.APPROVED);

        // then
        assertThat(changedBorrowStatus).isEqualTo(borrow);
    }

    @Test
    void shouldChangeBorrowStatusWhenStatusChangedToDevoted() {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                5,
                0,
                true,
                "desc");
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        // when
        Borrow changedBorrowStatus = systemUnderTest.changeBorrowStatus(ID, BorrowStatus.DEVOTED);

        // then
        assertThat(changedBorrowStatus).isEqualTo(borrow);
    }

    @Test
    void shouldNotChangeBorrowStatusWhenBookIsNotFound() {
        // given
        Set<Genre> genres = new HashSet<>();
        BorrowStatus status = BorrowStatus.NOT_APPROVED;

        Book book = new Book();
        Borrow borrow = new Borrow(ID,
                user,
                book,
                null,
                null,
                null,
                status);

        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.changeBorrowStatus(ID, BorrowStatus.APPROVED)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldNotChangeBorrowStatusWhenBorrowIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.changeBorrowStatus(ID, BorrowStatus.APPROVED)).isInstanceOf(BorrowNotFoundException.class);
    }
}