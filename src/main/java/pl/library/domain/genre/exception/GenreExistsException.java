package pl.library.domain.genre.exception;

public class GenreExistsException extends RuntimeException {
    public GenreExistsException(String exception) {
        super(exception);
    }
}
