package pl.library.domain.genre.repository;

import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenreRequest;

import java.util.List;

public interface GenreService {
    List<Genre> addManyGenres(GenreRequest genres);
    List<Genre> getAllGenres();
    void deleteManyGenres(GenreRequest ids);
}
