package pl.library.api.genre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.genre.exception.GenreExistsException;
import pl.library.domain.genre.exception.GenreNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GenreControllerAdvice {
    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleGenreNotFoundException(GenreNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 3);
        return error;
    }

    @ExceptionHandler(GenreExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleGenreExistsException(GenreExistsException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 4);
        return error;
    }
}
