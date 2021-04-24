package pl.library.adapters.mysql.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.library.adapters.mysql.model.genre.Genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    @Size(max = 200)
    private String author;
    @NotBlank
    @Size(max = 200)
    private String publisher;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Genre> genres;
    @Builder.Default
    private Integer count = 1;
    private Integer available;
    private Boolean status;
    @Column(length = 2000)
    @Lob
    private String description;
}
