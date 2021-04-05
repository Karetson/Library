package pl.library.api.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String details;
    private Integer errorCode;

    public ErrorResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public ErrorResponse(String message, String details, Integer errorCode) {
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }
}