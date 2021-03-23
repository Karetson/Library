package pl.library.adapters.mysql.model.genre;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(unique = true)
    private String genre;
}
