package pl.library.domain.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapters.mysql.model.genre.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Boolean existsByGenre(String genre);
}
