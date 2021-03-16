package pl.library.adapters.mysql.model.book;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
    private BookGenre genres;
    private Integer count;
    private Integer amount = 1;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "user_id")
//    private User user;
}