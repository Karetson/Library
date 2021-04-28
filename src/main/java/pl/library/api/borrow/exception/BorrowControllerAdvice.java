package pl.library.api.borrow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.borrow.exception.BorrowExistsException;
import pl.library.domain.borrow.exception.BorrowNotFoundException;

@RestControllerAdvice
public class BorrowControllerAdvice {
    @ExceptionHandler(BorrowNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleBorrowNotFoundException(BorrowNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 3);
        return error;
    }

    @ExceptionHandler(BorrowExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleBorrowExistsException(BorrowExistsException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 4);
        return error;
    }
}
