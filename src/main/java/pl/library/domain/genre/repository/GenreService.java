package pl.library.domain.genre.repository;

import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenresRequest;

import java.util.List;

public interface GenreService {
    List<Genre> multiAdd(GenresRequest genres);
    List<Genre> getAll();
    void multiDelete(GenresRequest ids);
}
