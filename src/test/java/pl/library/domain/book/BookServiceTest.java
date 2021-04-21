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
import pl.library.domain.genre.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {
    static final long ID = 1L;

    BookService systemUnderTest;
    Set<Genre> genres = Set.of(new Genre(1L, "Comedy"));
    Book book = new Book(ID,
            "title",
            "author",
            "publisher",
            genres,
            10,
            8,
            true,
            "desc");

    @Mock
    BookRepository bookRepository;
    @Mock
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BookService(bookRepository, genreRepository);
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
    void shouldNotReturnAllBooksBasedOnPhraseWhenBookIsNotFound() {
        // given
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
        String title = "title";
        when(bookRepository.findByIdAndTitle(ID, title)).thenReturn(Optional.of(book));

        // when
        Book bookByIdAndTitle = systemUnderTest.getBookByIdAndTitle(ID, title);

        // then
        assertThat(bookByIdAndTitle).isEqualTo(book);
    }

    @Test
    void shouldNotReturnBookBasedOnGivenIdAndTitleWhenBookIsNotFound() {
        // given
        String title = "title";

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getBookByIdAndTitle(ID, title)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnListOfBooksBasedOnGenres() {
        // given
        Genre genre = new Genre();
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(genre));
        when(bookRepository.findAllByGenres(any(Genre.class))).thenReturn(Optional.of(List.of(new Book())));

        // when
        List<Book> allBooksByGenres = systemUnderTest.getAllBooksByGenres(ID);

        // then
        assertThat(allBooksByGenres).containsExactly(new Book());
    }

    @Test
    void shouldNotReturnListOfBooksBasedOnGenresWhenBookIsNotFound() {
        // given
        Genre genre = new Genre();
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(genre));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByGenres(ID)).isInstanceOf(BookNotFoundException.class);
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
    void shouldNotReturnListOfBooksBasedOnStatusWhenBookIsNotFound() {
        // given
        Boolean status = false;

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByStatus(status)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldCreateBook() {
        // given
        when(bookRepository.save(book)).thenReturn(book);

        // when
        Book createdBook = systemUnderTest.addBook(book);

        // then
        assertThat(createdBook).isEqualTo(book);
    }

    @Test
    void shouldNotCreateBookWhenTitleAndAuthorAlreadyExists() {
        // given
        when(bookRepository.existsByTitleAndAuthor(any(String.class), any(String.class)))
                .thenThrow(BookExistsException.class);

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
        Book book = new Book(ID,
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
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book updatedBook = systemUnderTest.updateBook(ID, book);
        bookRepository.save(updatedBook);

        // then
        assertThat(updatedBook).isEqualTo(book);
    }

    @Test
    void shouldUpdateBookWhenAvailableEquals0() {
        // given
        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                0,
                0,
                null,
                "desc");
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book updatedBook = systemUnderTest.updateBook(ID, book);
        bookRepository.save(updatedBook);

        // then
        assertThat(updatedBook).isEqualTo(book);
    }

    @Test
    void shouldNotUpdateBookWhenAvailablePlusDifferentBetweenCountsLT0() {
        // given
        Book book = new Book(ID,
                "title",
                "author",
                "publisher",
                genres,
                0,
                -5,
                null,
                "desc");
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.updateBook(ID, book)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldNotUpdateBookWhenBookIsNotFound() {
        // given
        Book book = new Book();

        // then

        assertThatThrownBy(() -> systemUnderTest.updateBook(ID, book)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldDeleteBookBasedOnGivenId() {
        // given
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));

        // when
        systemUnderTest.deleteBook(ID);

        // then
        verify(bookRepository).deleteById(ID);
    }

    @Test
    void shouldNotDeleteBookBasedOnGivenIdWhenBookIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBook(ID)).isInstanceOf(BookNotFoundException.class);
    }
}