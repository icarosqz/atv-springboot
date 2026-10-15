package biblioteca.biblioteca.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroPostPutDto {
    @NotBlank(message = "ISBN não pode ser vazio")
    private String isbn;

    @NotBlank(message = "Título não pode ser vazio")
    private String titulo;

    @NotNull(message = "Autor é obrigatório")
    @Positive(message = "ID do autor deve ser positivo")
    private Long autorId;

    @NotNull(message = "Ano é obrigatório")
    @Positive(message = "Ano deve ser positivo")
    private Integer ano;

    @NotNull(message = "Edição é obrigatória")
    @Positive(message = "Edição deve ser positiva")
    private Integer edicao;
}