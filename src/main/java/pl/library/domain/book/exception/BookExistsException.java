package pl.library.domain.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.CONFLICT)
public class BookExistsException extends RuntimeException {
    public BookExistsException(String exception) {
        super(exception);
    }
}
