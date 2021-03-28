package pl.library.adapters.mysql.model.book;

import lombok.*;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.genre.Genre;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
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
    @ManyToMany
    private Set<Genre> genres;
    private Integer count;
    private Integer available = 1;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Borrow> borrows;
}