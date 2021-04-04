package pl.library.api.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.GenreRequest;
import pl.library.api.genre.dto.CreateGenreResponse;
import pl.library.api.genre.dto.GetGenreResponse;
import pl.library.domain.genre.GenreServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookGenre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreServiceImpl genreService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CreateGenreResponse> addManyGenres(@Valid @RequestBody GenreRequest genres) {
        List<Genre> addedGenres = genreService.multiAdd(genres);
        return addedGenres.stream().map(CreateGenreResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/search/all")
    public List<GetGenreResponse> getAllGenres() {
        List<Genre> gainedGenres = genreService.getAll();
        return gainedGenres.stream().map(GetGenreResponse::new).collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteManyGenres(@RequestBody GenreRequest ids) {
        genreService.multiDelete(ids);
    }
}
