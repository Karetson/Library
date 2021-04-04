package pl.library.domain.genre.repository;

import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenreRequest;

import java.util.List;

public interface GenreService {
    List<Genre> multiAdd(GenreRequest genres);
    List<Genre> getAll();
    void multiDelete(GenreRequest ids);
}
