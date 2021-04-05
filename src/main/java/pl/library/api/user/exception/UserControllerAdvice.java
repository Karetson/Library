package pl.library.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record not found", details, 3);
        return error;
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleUserExistsException(UserExistsException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record already exists", details, 4);
        return error;
    }
}
