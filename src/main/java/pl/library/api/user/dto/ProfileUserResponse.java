package pl.library.api.user.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProfileUserResponse {
    String firstName;
    String lastName;
    String email;
    LocalDateTime createdAt;
}
