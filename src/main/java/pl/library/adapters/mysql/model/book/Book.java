package pl.library.adapters.mysql.model.book;

import lombok.*;
import pl.library.adapters.mysql.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title must not be empty")
    private String title;
    @NotEmpty(message = "Author must not be empty")
    private String author;
    @NotEmpty(message = "Publisher must not be empty")
    private String publisher;
    @NotNull(message = "Invalid book type")
    private BookType bookType;
    private Integer amount = 1;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
}