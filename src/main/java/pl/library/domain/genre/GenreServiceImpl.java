package pl.library.domain.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenresRequest;
import pl.library.domain.genre.exception.GenreExistsException;
import pl.library.domain.genre.exception.GenreNotFoundException;
import pl.library.domain.genre.repository.GenreRepository;
import pl.library.domain.genre.repository.GenreService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public List<Genre> multiAdd(GenresRequest genresRequest) {
        Set<Genre> genres = new HashSet<>();

        for (String e : genresRequest.getGenres()) {
            if (!genreRepository.existsByName(e)) {
                Genre genre = new Genre();
                genre.setName(e);
                genres.add(genre);
            } else {
                throw new GenreExistsException("Genre with name: " + e + " already exists!");
            }
        }

        return genreRepository.saveAll(genres);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll().orElseThrow(()
                -> new GenreNotFoundException("There are no genres"));
    }

    @Override
    @Transactional
    public void multiDelete(GenresRequest ids) {
        Set<Genre> genres = new HashSet<>();

        for (Long e : ids.getIds()) {
            Genre genre = genreRepository.findById(e).orElseThrow(()
                    -> new GenreNotFoundException("Genre not found!"));
            genres.add(genre);
        }

        genreRepository.deleteAll(genres);
    }
}
