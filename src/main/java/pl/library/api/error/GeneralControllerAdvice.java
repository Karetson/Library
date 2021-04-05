package pl.library.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GeneralControllerAdvice {
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleArithmeticException(ArithmeticException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("The number entered is incorrect", details, 2);
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Marked fields cannot be empty", 1);
        return error;
    }
}
