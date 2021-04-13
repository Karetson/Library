package pl.library.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceImplTest {
    BookServiceImpl systemUnderTest;
    Set<Genre> genres = new HashSet<>();

    @Mock
    BookRepository bookRepository;

    static final long BOOK_ID = 1L;

    @BeforeEach
    void beforeEachTestMethod() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BookServiceImpl(bookRepository);
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
        assertThatThrownBy(() -> systemUnderTest.addBook(book)).isInstanceOf(BookExistsException.class);
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
    void shouldUpdateBook() {
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(BOOK_ID,
                "title",
                "author",
                "publisher",
                genres,
                10,
                10,
                true,
                "description of book"));
        Book book = new Book();
        bookRepository.save(book);
        Book updatedBook = new Book();
        updatedBook.setTitle("updated title");
        updatedBook.setAuthor("updated author");
        // when
        Book updating = systemUnderTest.updateBook(BOOK_ID, updatedBook);

        // then
        assertThat(updating).isInstanceOf(Book.class);
        assertThat(book).isEqualTo(updatedBook);
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
        // when

        // then
    }
}