package pl.library.api.genre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GenresRequest {
    List<Long> ids;
    List<String> genres;
}
