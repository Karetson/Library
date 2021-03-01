package pl.library.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String s) {
        super(s);
    }
}
