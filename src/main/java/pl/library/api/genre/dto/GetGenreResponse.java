package pl.library.api.genre.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.genre.Genre;

@Value
public class GetGenreResponse {
    long id;
    String name;

    public GetGenreResponse(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
