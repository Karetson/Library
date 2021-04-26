package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapters.mysql.model.user.User;

@Value
public class CreateUserRequest {
    private static final long serialVersionUID = 5926468583005150707L;

    String firstName;
    String lastName;
    String email;
    String password;

    public User toUser() {
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
