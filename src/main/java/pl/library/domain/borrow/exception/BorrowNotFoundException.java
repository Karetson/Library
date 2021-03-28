package pl.library.domain.borrow.exception;

public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(String exception) {
        super(exception);
    }
}
