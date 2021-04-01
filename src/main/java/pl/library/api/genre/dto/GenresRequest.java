package pl.library.api.genre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GenresRequest {
    List<Long> ids;
    List<String> genres;
}
