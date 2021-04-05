package pl.library.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GeneralControllerAdvice {
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleArithmeticException(ArithmeticException exception) {
        String details = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse("The number entered is incorrect", details, 2);
        return error;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            NullPointerException.class,
            ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleValidationException() {
        ErrorResponse error = new ErrorResponse("Marked fields cannot be empty", 1);
        return error;
    }
}
