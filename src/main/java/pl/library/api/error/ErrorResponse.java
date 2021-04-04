package pl.library.api.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private List<String> details;
//    private Integer errorCode;

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}