package pl.library.domain.genre.repository;

import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;

public interface GenreService {
    Genre addition(Genre genre);
    List<Genre> getAll();
    void delete(Long id);
}
