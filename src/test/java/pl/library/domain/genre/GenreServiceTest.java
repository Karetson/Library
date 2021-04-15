package pl.library.domain.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.domain.genre.repository.GenreRepository;

@RunWith(MockitoJUnitRunner.class)
class GenreServiceTest {
    GenreService systemUnderTest;
    @Mock
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new GenreService(genreRepository);
    }
}