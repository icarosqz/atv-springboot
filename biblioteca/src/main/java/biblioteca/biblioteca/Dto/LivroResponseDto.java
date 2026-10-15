package biblioteca.biblioteca.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroResponseDto {
    private Long id;
    private String isbn;
    private String titulo;
    private AutorResponseDto autor;
    private Integer ano;
    private Integer edicao;
}