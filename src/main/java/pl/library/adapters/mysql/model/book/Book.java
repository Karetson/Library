package pl.library.adapters.mysql.model.book;

import lombok.*;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.genre.Genre;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title must not be empty")
    @Size(max = 50)
    private String title;
    @NotBlank(message = "Author must not be empty")
    @Size(max = 40)
    private String author;
    @NotBlank(message = "Publisher must not be empty")
    @Size(max = 80)
    private String publisher;
    @ManyToMany
    private Set<Genre> genres;
    @Builder.Default
    private Integer count = 1;
    private Integer available;
    @OneToMany(mappedBy = "book")
    private Set<Borrow> borrows;
    @Column(length = 2000)
    @Lob
    private String description;
}