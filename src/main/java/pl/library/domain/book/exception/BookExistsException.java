package pl.library.domain.book.exception;

public class BookExistsException extends RuntimeException {
    public BookExistsException(String exception) {
        super(exception);
    }
}
