package pl.library.api.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenresRequest;
import pl.library.domain.genre.GenreServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/bookGenre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreServiceImpl genreService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Genre> multiAddGenres(@Valid @RequestBody GenresRequest genres) {
        return genreService.multiAdd(genres);
    }

    @GetMapping("/search/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void multiDeleteGenres(@RequestBody GenresRequest ids) {
        genreService.multiDelete(ids);
    }
}
