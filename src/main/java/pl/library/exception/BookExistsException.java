package pl.library.exception;

public class BookExistsException extends Exception {
    public BookExistsException(String s) {
        super(s);
    }
}
