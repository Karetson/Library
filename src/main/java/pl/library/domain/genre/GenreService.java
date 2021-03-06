package pl.library.domain.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.CreateGenreRequest;
import pl.library.domain.genre.exception.GenreExistsException;
import pl.library.domain.genre.exception.GenreNotFoundException;
import pl.library.domain.genre.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    public List<Genre> addManyGenres(CreateGenreRequest genresRequest) {
//        try {
//            Set<Genre> genres = genresRequest.getGenres().stream()
//                    .filter(s -> !genreRepository.existsByName(getAllGenres().toString()))
//                    .map(genre -> new Genre(null, genre)).collect(Collectors.toSet());
//
//            return genreRepository.saveAll(genres);
//        } catch (DataIntegrityViolationException exception) {
//            throw new GenreExistsException("These genres already exists!");
//        }

        Set<Genre> genres = new HashSet<>();

        for (String e : genresRequest.getGenres()) {
            if (!genreRepository.existsByName(e)) {
                Genre genre = new Genre();
                genre.setName(e);
                genres.add(genre);
            } else {
                throw new GenreExistsException("These genres already exists!");
            }
        }

        return genreRepository.saveAll(genres);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.getAll().orElseThrow(()
                -> new GenreNotFoundException("There are no genres"));
    }

    @Transactional
    public void deleteManyGenres(CreateGenreRequest ids) {
        Set<Genre> genres = new HashSet<>();

        for (Long e : ids.getIds()) {
            Genre genre = genreRepository.findById(e).orElseThrow(()
                    -> new GenreNotFoundException("Genre not found!"));
            genres.add(genre);
        }

        genreRepository.deleteAll(genres);
    }
}
