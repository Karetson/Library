package pl.library.domain.borrow.exception;

public class BorrowExistsException extends RuntimeException {
    public BorrowExistsException(String exception) {
        super(exception);
    }
}
