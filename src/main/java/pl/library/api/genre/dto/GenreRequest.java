package pl.library.api.genre.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;

@Value
public class GenreRequest {
    String name;
    List<Long> ids;
    List<String> genres;

    public Genre toGenre() {
        return Genre.builder()
                .name(this.name)
                .build();
    }
}
