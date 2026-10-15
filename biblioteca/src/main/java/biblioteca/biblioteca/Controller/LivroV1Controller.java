package biblioteca.biblioteca.Controller;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Service.LivroCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/v1/livros",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LivroV1Controller {
    private final LivroCrudService livroCrudService;

    @PostMapping
    public ResponseEntity<LivroResponseDto> criarLivro(
            @RequestBody @Valid LivroPostPutDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroCrudService.criarLivro(dto));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> buscarLivros(
            @RequestParam(name = "isbn", required = false) String isbn,
            @RequestParam(name = "titulo", required = false) String titulo) {
        return ResponseEntity.ok(livroCrudService.buscarLivros(isbn, titulo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivro(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(livroCrudService.buscarLivro(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarLivro(
            @PathVariable("id") Long id,
            @RequestBody @Valid LivroPostPutDto dto) {
        return ResponseEntity.ok(livroCrudService.atualizarLivro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable("id") Long id) {
        livroCrudService.removerLivro(id);
        return ResponseEntity.noContent().build();
    }
}