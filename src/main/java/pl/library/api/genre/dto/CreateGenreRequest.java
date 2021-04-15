package pl.library.api.genre.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.genre.Genre;

import java.util.List;

@Value
public class CreateGenreRequest {
    List<Long> ids;
    List<String> genres;

    public Genre toGenre() {
        return Genre.builder()
                .id(this.ids.stream().iterator().next())
                .name(this.genres.stream().iterator().next())
                .build();
    }
}
