package pl.library.api.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.genre.Genre;
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
    public Genre addGenre(@Valid @RequestBody Genre genre) {
        return genreService.addition(genre);
    }

    @GetMapping("/search/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@RequestParam Long id) {
        genreService.delete(id);
    }
}
