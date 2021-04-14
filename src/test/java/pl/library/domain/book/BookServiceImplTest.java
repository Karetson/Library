package pl.library.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceImplTest {
    BookService systemUnderTest;
    Set<Genre> genres = new HashSet<>();

    @Autowired
    @Mock
    BookRepository bookRepository;

    static final long BOOK_ID = 1L;

    @BeforeEach
    void beforeEachTestMethod() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BookService(bookRepository);
        genres.add(new Genre(1L, "Comedy"));
    }

    @Test
    void shouldReturnAllBooksBasedOnPhrase() {
        // given
        when(bookRepository.findAllByTitleOrAuthorLike(any(String.class))).thenReturn(Optional.of(List.of(new Book())));
        String phrase = "Harry";
        // when
        List<Book> allBooksByPhrase = systemUnderTest.getAllBooksByPhrase(phrase);
        // then
        assertThat(allBooksByPhrase).containsExactly(new Book());
    }

    @Test
    void shouldNotReturnAllBooksBasedOnPhrase() {
        // given
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);
        String phrase = "Harry";
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByPhrase(phrase)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnListOfBooksBasedOnGivenNumberGT0() {
        // given
        when(bookRepository.findRandomByNumber(any(Byte.class))).thenReturn(List.of(new Book()));
        Byte number = 5;
        // when
        List<Book> randomBooksByNumber = systemUnderTest.getRandomBooksByNumber(number);
        // then
        assertThat(randomBooksByNumber).containsExactly(new Book());
    }

    @Test
    void shouldNotReturnListOfBooksBasedOnGivenNumberLTE0() {
        // given
        Byte number = -2;
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getRandomBooksByNumber(number)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldReturnBookBasedOnGivenIdAndTitle() {
        // given
        when(bookRepository.findByIdAndTitle(any(Long.class), any(String.class))).thenReturn(Optional.of(new Book()));
        String title = "Harry";
        // when
        Book bookByIdAndTitle = systemUnderTest.getBookByIdAndTitle(BOOK_ID, title);
        // then
        assertThat(bookByIdAndTitle).isInstanceOf(Book.class);
    }

    @Test
    void shouldNotReturnBookBasedOnGivenIdAndTitle() {
        // given
        Book book = new Book();
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        String title = "Harry";
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getBookByIdAndTitle(BOOK_ID, title)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnListOfBooksBasedOnGenres() {
        // given
        when(bookRepository.findAllByGenres(any(Genre.class))).thenReturn(Optional.of(List.of(new Book())));
        Genre genre = new Genre();
        // when
        List<Book> allBooksByGenres = systemUnderTest.getAllBooksByGenres(genre);
        // then
        assertThat(allBooksByGenres).containsExactly(new Book());
    }

    @Test
    void shouldNotReturnListOfBooksBasedOnGenres() {
        // given
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);
        Genre genre = new Genre();
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByGenres(genre)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnListOfBooksBasedOnStatus() {
        // given
        when(bookRepository.findALlByStatus(any(Boolean.class))).thenReturn(Optional.of(List.of(new Book())));
        Boolean status = false;
        // when
        List<Book> allBooksByStatus = systemUnderTest.getAllBooksByStatus(status);
        // then
        assertThat(allBooksByStatus).containsExactly(new Book());
    }

    @Test
    void shouldNotReturnListOfBooksBasedOnStatus() {
        // given
        List<Book> books = new ArrayList<>();
        Boolean status = false;
        when(bookRepository.findAll()).thenReturn(books);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByStatus(status)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldCreateBook() {
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                10,
                8,
                true,
                "desc");
        // when
        Book createdBook = systemUnderTest.addBook(book);
        // then
        assertThat(createdBook).isInstanceOf(Book.class);
    }

    @Test
    void shouldNotCreateBookWhenTitleAndAuthorAlreadyExists() {
        // given
        when(bookRepository.existsByTitleAndAuthor(any(String.class), any(String.class)))
                .thenThrow(new BookExistsException("Already exists!"));

        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                10,
                8,
                true,
                "desc");
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBook(book)).isInstanceOf(
                BookExistsException.class);
    }

    @Test
    void shouldNotCreateBookWhenGenreIsEmpty() {
        // given
        Book book = new Book();
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBook(book)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotCreateBookWhenCountLTE0() {
        // given
        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                0,
                null,
                true,
                "desc");
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBook(book)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldUpdateBookWhenAvailableGT0() {
        // given
        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                10,
                10,
                null,
                "desc");
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        // when
        Book updatedBook = systemUnderTest.updateBook(BOOK_ID, book);
        bookRepository.save(updatedBook);
        // then
        assertThat(updatedBook).isEqualTo(book);
    }

    @Test
    void shouldUpdateBookWhenAvailableEquals0() {
        // given
        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                0,
                0,
                null,
                "desc");
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        // when
        Book updatedBook = systemUnderTest.updateBook(BOOK_ID, book);
        bookRepository.save(updatedBook);
        // then
        assertThat(updatedBook).isEqualTo(book);
    }

    @Test
    void shouldNotUpdateBookWhenAvailablePlusDifferentBetweenCountsLT0() {
        // given
        Book book = new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                0,
                -5,
                null,
                "desc");
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.updateBook(BOOK_ID, book)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldNotUpdateBookWhenDoesNotExists() {
        // given
        Book book = new Book();
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.updateBook(BOOK_ID, book)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldDeleteBookBasedOnGivenId() {
        // given
        Book book = new Book();
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        // when
        systemUnderTest.deleteBook(BOOK_ID);
        // then
        verify(bookRepository).deleteById(BOOK_ID);
    }

    @Test
    void shouldNotDeleteBookBasedOnGivenId() {
        // given
        Book book = new Book();
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBook(BOOK_ID)).isInstanceOf(BookNotFoundException.class);
    }
}