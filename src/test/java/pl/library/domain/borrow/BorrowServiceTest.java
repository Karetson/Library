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

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    Set<Genre> genres = new HashSet<>();
    BorrowStatus status = BorrowStatus.NOT_APPROVED;

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

    @Test
    void shouldCreateBorrow() throws UserNotFoundException {
        // given
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
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldNotCreateBorrowWhenBorrowAlreadyExists() {
        // given
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
        book.setAvailable(0);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBorrow(borrow)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldChangeBorrowStatusWhenStatusChangedToApproved() {
        // given
        book.setAvailable(0);
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
        book.setAvailable(0);
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
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(new Book()));

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

    @Test
    void shouldDeleteBorrowBasedOnId() {
        // given
        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        // when
        systemUnderTest.deleteBorrow(ID);

        // then
        verify(borrowRepository).deleteById(ID);
    }

    @Test
    void shouldNotDeleteBorrowBasedOnIDWhenBorrowIsNotFound() {
        // given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBorrow(ID)).isInstanceOf(BorrowNotFoundException.class);
    }

    @Test
    void shouldNotDeleteBorrowBasedOnIdWhenBookIsNotFound() {
        // given
        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBorrow(ID)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldNotDeleteBorrowBasedOnIdWhenBorrowStatusIsNotEqualToNotApproved() {
        // given
        borrow.setStatus(BorrowStatus.APPROVED);
        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBorrow(ID)).isInstanceOf(ValidationException.class);
    }
}