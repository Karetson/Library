package pl.library.domain.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.domain.genre.exception.GenreExistsException;
import pl.library.domain.genre.exception.GenreNotFoundException;
import pl.library.domain.genre.repository.GenreRepository;
import pl.library.domain.genre.repository.GenreService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Genre addition(Genre genre) {
        if (genreRepository.existsByGenre(genre.getGenre())) {
            throw new GenreExistsException("Genre: '" + genre + "' already exists!");
        } else {
            return genreRepository.save(genre);
        }
    }

    @Override
    public List<Genre> getAll() {
        if (genreRepository.findAll().size() > 0) {
            return genreRepository.findAll();
        } else {
            throw new GenreNotFoundException("There are no genres.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!genreRepository.findById(id).isPresent()) {
            throw new GenreNotFoundException("Genre with id: '" + id + "' not found!");
        } else {
            genreRepository.deleteById(id);
        }
    }
}
