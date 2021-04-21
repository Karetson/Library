package pl.library.api.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;

@RestControllerAdvice
public class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleBookNotFoundException(BookNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 3);
        return error;
    }

    @ExceptionHandler(BookExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleBookExistsException(BookExistsException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 4);
        return error;
    }
}