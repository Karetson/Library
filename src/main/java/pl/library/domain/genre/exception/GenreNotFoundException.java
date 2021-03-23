package pl.library.domain.genre.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String exception) {
        super(exception);
    }
}
