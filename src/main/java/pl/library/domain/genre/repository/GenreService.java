package pl.library.domain.genre.repository;

import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.CreateGenreRequest;
import pl.library.api.genre.dto.DeleteGenreRequest;

import java.util.List;

public interface GenreService {
    List<Genre> addManyGenres(CreateGenreRequest genres);
    List<Genre> getAllGenres();
    void deleteManyGenres(DeleteGenreRequest ids);
}
