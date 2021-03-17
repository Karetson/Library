package pl.library.domain.book.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String exception) {
        super(exception);
    }
}
