package pl.library.adapters.mysql.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.borrow.Borrow;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(max = 25)
    private String firstName;
    @NotBlank
    @Size(max = 25)
    private String lastName;
    @Email  // default max size 80
    private String email;
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&+=]).*$",
            message = "The password must be at least 8 characters long. One uppercase letter, one lowercase letter, one number and a special character(@#$%^!&+=)")
    @Size(max = 20)
    private String password;
    @Builder.Default
    private UserRole role = UserRole.USER;
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy(value = "title")
    private Set<Book> favoriteBooks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Borrow> borrows;
    private LocalDateTime createdAt = LocalDateTime.now();
}