package pl.library.api.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Getter
@Setter
public class UpdateUserRequest {
    private Long id;
    public String firstName;
    public String lastName;
    private String email;
    private String password;
}
