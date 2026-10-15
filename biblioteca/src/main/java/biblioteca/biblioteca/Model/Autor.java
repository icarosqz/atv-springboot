package biblioteca.biblioteca.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "tb_autor")
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "ds_nomeAutor", nullable = false)
    @JsonProperty("nomeAutor")
    private String nomeAutor;

    @Column(name = "ds_nacionalidade", nullable = false)
    @JsonProperty("nacionalidade")
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.REMOVE)
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Livro> livros;

}