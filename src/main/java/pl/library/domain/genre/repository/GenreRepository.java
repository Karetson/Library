package pl.library.domain.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Boolean existsByName(String name);

    @Query(value = "SELECT * FROM genres", nativeQuery = true)
    Optional<List<Genre>> getAll();
}
