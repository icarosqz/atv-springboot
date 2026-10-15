package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.AutorPostPutDto;
import biblioteca.biblioteca.Dto.AutorResponseDto;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Repository.AutorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorCrudPadraoService implements AutorCrudService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AutorRepository autorRepository;

    @Override
    public AutorResponseDto criarAutor(AutorPostPutDto autorPostPutDto) {
        Autor autor = modelMapper.map(autorPostPutDto, Autor.class);
        return modelMapper.map(autorRepository.save(autor), AutorResponseDto.class);
    }

    @Override
    public List<AutorResponseDto> buscarTodosAutores() {
        return autorRepository.findAll()
                .stream()
                .map(autor -> modelMapper.map(autor, AutorResponseDto.class))
                .toList();
    }

    @Override
    public AutorResponseDto buscarAutor(Long id) {
        return modelMapper.map(obterAutorExistente(id), AutorResponseDto.class);
    }

    @Override
    public AutorResponseDto atualizarAutor(Long id, AutorPostPutDto autorPostPutDto) {
        Autor autor = obterAutorExistente(id);
        autor.setNomeAutor(autorPostPutDto.getNomeAutor());
        autor.setNacionalidade(autorPostPutDto.getNacionalidade());
        return modelMapper.map(autorRepository.save(autor), AutorResponseDto.class);
    }

    @Override
    @Transactional
    public void removerAutor(Long id) {
        autorRepository.delete(obterAutorExistente(id));
    }

    // id inexistente = 404
    private Autor obterAutorExistente(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
    }
}