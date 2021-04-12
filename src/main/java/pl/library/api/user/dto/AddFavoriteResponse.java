package pl.library.api.user.dto;

import lombok.Value;
import pl.library.api.book.dto.GetBookResponse;

import java.util.Set;

@Value
public class AddFavoriteResponse {
    long id;
    Set<GetBookResponse> favoriteBooks;
}
