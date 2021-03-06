package pl.library.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.ValidationException;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 3);
        return error;
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleUserExistsException(UserExistsException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 4);
        return error;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleValidationException(ValidationException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message, 5);
        return error;
    }
}
