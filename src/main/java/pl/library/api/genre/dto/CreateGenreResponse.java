package pl.library.api.genre.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.genre.Genre;

@Value
public class CreateGenreResponse {
    long id;

    public CreateGenreResponse(Genre genre) {
        this.id = genre.getId();
    }
}
