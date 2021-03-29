package pl.library.adapters.mysql.model.user;

import lombok.*;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&+=]).*$",
            message = "The password must be at least 8 characters long. One uppercase letter, one lowercase letter, one number and a special character(@#$%^!&+=)")
    private String password;
    @Builder.Default
    private UserRole role = UserRole.USER;
    @ManyToMany
    private Set<Book> favoriteBooks;
    @OneToMany(mappedBy = "user")
    private Set<Borrow> borrows;
    private LocalDateTime createdAt = LocalDateTime.now();
}