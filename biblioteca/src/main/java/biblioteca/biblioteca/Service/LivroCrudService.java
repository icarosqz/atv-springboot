package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;

import java.util.List;

public interface LivroCrudService {
    LivroResponseDto criarLivro(LivroPostPutDto dto);

    List<LivroResponseDto> buscarLivros(String isbn, String titulo);

    LivroResponseDto buscarLivro(Long id);

    LivroResponseDto atualizarLivro(Long id, LivroPostPutDto dto);

    void removerLivro(Long id);
}