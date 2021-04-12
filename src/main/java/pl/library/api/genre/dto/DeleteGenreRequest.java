package pl.library.api.genre.dto;

import lombok.Value;

import java.util.List;

@Value
public class DeleteGenreRequest {
    List<Long> ids;
}
