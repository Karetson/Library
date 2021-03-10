package pl.library.domain.service.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.CONFLICT)
public class BookExistsException extends RuntimeException {
    public BookExistsException(String s) {
        super(s);
    }
}
