package pl.library.domain.adapters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.domain.book.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Profile("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test() {
        Optional<List<Book>> bookList = bookRepository.findAllByTitleOrAuthorLike("Harry");
        assertThat(bookList.get()).isNotEmpty();
    }
}
