package biblioteca.biblioteca.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_livro")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ds_isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "ds_titulo", nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "nr_ano", nullable = false)
    private Integer ano;

    @Column(name = "nr_edicao", nullable = false)
    private Integer edicao;
}