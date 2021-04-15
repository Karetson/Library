package pl.library.domain.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.CreateGenreRequest;
import pl.library.domain.genre.exception.GenreExistsException;
import pl.library.domain.genre.exception.GenreNotFoundException;
import pl.library.domain.genre.repository.GenreRepository;

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
class GenreServiceTest {
    private static final Long ID = 1L;
    GenreService systemUnderTest;
    @Mock
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new GenreService(genreRepository);
    }

    @Test
    void shouldReturnAllGenres() {
        // given
        when(genreRepository.getAll()).thenReturn(Optional.of(List.of(new Genre())));

        // when
        List<Genre> allGenres = systemUnderTest.getAllGenres();

        // then
        assertThat(allGenres).containsExactly(new Genre());
    }

    @Test
    void shouldNotReturnAllGenresWhenGenresAreNotExists() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllGenres()).isInstanceOf(GenreNotFoundException.class);
    }

    @Test
    void shouldAddManyGenres() {
        // given
        Set<Genre> genres = new HashSet<>();
        List<String> names = new ArrayList<>();
        names.add("Test");
        when(genreRepository.saveAll(genres)).thenReturn(List.of(new Genre()));
        CreateGenreRequest genreRequest = new CreateGenreRequest(null, names);

        // when
        List<Genre> genreList = systemUnderTest.addManyGenres(genreRequest);

        // then
        assertThat(genreList).containsExactly();
    }

    @Test
    void shouldNotAddGenreWhenAlreadyExists() {
        // given
        List<String> names = new ArrayList<>();
        names.add("Test");
        when(genreRepository.existsByName(any(String.class))).thenReturn(true);
        CreateGenreRequest genreRequest = new CreateGenreRequest(null, names);

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addManyGenres(genreRequest)).isInstanceOf(GenreExistsException.class);
    }

    @Test
    void shouldDeleteManyGenres() {
        // given
        Set<Genre> genres = new HashSet<>();
        List<Long> ids = new ArrayList<>();
        ids.add(ID);
        Genre genre = new Genre(ID, null);
        genres.add(genre);
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(genre));
        CreateGenreRequest genreRequest = new CreateGenreRequest(ids, null);

        // when
        systemUnderTest.deleteManyGenres(genreRequest);

        // then
        verify(genreRepository).deleteAll(genres);
    }

    @Test
    void shouldNotDeleteGenreWhenIsNotFound() {
        // given
        List<Long> ids = new ArrayList<>();
        ids.add(ID);
        CreateGenreRequest genreRequest = new CreateGenreRequest(ids, null);

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteManyGenres(genreRequest)).isInstanceOf(GenreNotFoundException.class);
    }
}