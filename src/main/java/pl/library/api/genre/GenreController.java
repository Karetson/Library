package pl.library.api.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.genre.dto.CreateGenreRequest;
import pl.library.api.genre.dto.CreateGenreResponse;
import pl.library.api.genre.dto.GetGenreResponse;
import pl.library.domain.genre.GenreService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookGenre/auth")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    // adding many book genres
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public List<CreateGenreResponse> addManyGenres(@Valid @RequestBody CreateGenreRequest genres) {
        List<Genre> addedGenres = genreService.addManyGenres(genres);
        return addedGenres.stream().map(CreateGenreResponse::new).collect(Collectors.toList());
    }

    // search for all genres
    @GetMapping("/search/all")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public List<GetGenreResponse> getAllGenres() {
        List<Genre> gainedGenres = genreService.getAllGenres();
        return gainedGenres.stream().map(GetGenreResponse::new).collect(Collectors.toList());
    }

    // removal of all genres
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public void deleteManyGenres(@Valid @RequestBody CreateGenreRequest ids) {
        genreService.deleteManyGenres(ids);
    }
}
