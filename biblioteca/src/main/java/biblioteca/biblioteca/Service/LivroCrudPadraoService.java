package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.AutorResponseDto;
import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.AutorRepository;
import biblioteca.biblioteca.Repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LivroCrudPadraoService implements LivroCrudService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    @Override
    @Transactional
    public LivroResponseDto criarLivro(LivroPostPutDto dto) {
        String isbn = dto.getIsbn().strip();

        if (livroRepository.existsByIsbn(isbn)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "ISBN já cadastrado");
        }

        Livro livro = new Livro();
        preencherLivro(livro, dto);
        return converter(livroRepository.save(livro));
    }

    @Override
    public List<LivroResponseDto> buscarLivros(String isbn, String titulo) {
        String filtroIsbn = isbn == null ? "" : isbn.strip();
        String filtroTitulo = titulo == null ? "" : titulo.strip();

        List<Livro> livros;

        if (!filtroIsbn.isEmpty() && !filtroTitulo.isEmpty()) {
            livros = livroRepository
                    .findByIsbnContainingIgnoreCaseAndTituloContainingIgnoreCase(
                            filtroIsbn, filtroTitulo);
        } else if (!filtroIsbn.isEmpty()) {
            livros = livroRepository.findByIsbnContainingIgnoreCase(filtroIsbn);
        } else if (!filtroTitulo.isEmpty()) {
            livros = livroRepository.findByTituloContainingIgnoreCase(filtroTitulo);
        } else {
            livros = livroRepository.findAll();
        }

        return livros.stream().map(this::converter).toList();
    }

    @Override
    public LivroResponseDto buscarLivro(Long id) {
        return converter(obterLivroExistente(id));
    }

    @Override
    @Transactional
    public LivroResponseDto atualizarLivro(Long id, LivroPostPutDto dto) {
        Livro livro = obterLivroExistente(id);
        String isbn = dto.getIsbn().strip();

        if (livroRepository.existsByIsbnAndIdNot(isbn, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "ISBN já cadastrado em outro livro");
        }

        preencherLivro(livro, dto);
        return converter(livroRepository.save(livro));
    }

    @Override
    @Transactional
    public void removerLivro(Long id) {
        livroRepository.delete(obterLivroExistente(id));
    }

    private Livro obterLivroExistente(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    private Autor obterAutorExistente(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Autor não encontrado"));
    }

    private void preencherLivro(Livro livro, LivroPostPutDto dto) {
        Autor autor = obterAutorExistente(dto.getAutorId());

        livro.setIsbn(dto.getIsbn().strip());
        livro.setTitulo(dto.getTitulo().strip());
        livro.setAutor(autor);
        livro.setAno(dto.getAno());
        livro.setEdicao(dto.getEdicao());
    }

    private LivroResponseDto converter(Livro livro) {
        Autor autor = livro.getAutor();

        AutorResponseDto autorDto = AutorResponseDto.builder()
                .id(autor.getId())
                .nomeAutor(autor.getNomeAutor())
                .nacionalidade(autor.getNacionalidade())
                .build();

        return LivroResponseDto.builder()
                .id(livro.getId())
                .isbn(livro.getIsbn())
                .titulo(livro.getTitulo())
                .autor(autorDto)
                .ano(livro.getAno())
                .edicao(livro.getEdicao())
                .build();
    }
}