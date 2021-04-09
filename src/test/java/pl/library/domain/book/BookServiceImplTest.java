package pl.library.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.book.repository.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceImplTest {
    BookServiceImpl systemUnderTest;
    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BookServiceImpl(bookRepository);
    }

    @Test
    void shouldReturnListOfBooksWhenCalledWithInputNumberGT0() {
        // given
        when(bookRepository.findRandomByNumber(any(Byte.class))).thenReturn(List.of(new Book()));
        Byte number = 5;
        // when
        List<Book> randomBooksByNumber = systemUnderTest.getRandomBooksByNumber(number);
        // then
        assertThat(randomBooksByNumber).containsExactly(new Book());
    }

    @Test
    void shouldReturnListOfBooksWhenCalledWithInputNumberLTE0() {
        // given
        Byte number = -2;
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getRandomBooksByNumber(number)).isInstanceOf(ArithmeticException.class);
    }
}