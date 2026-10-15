package biblioteca.biblioteca.Repository;

import biblioteca.biblioteca.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIsbnContainingIgnoreCase(String text);

    List<Livro> findByTituloContainingIgnoreCase(String text);

    List<Livro> findByIsbnContainingIgnoreCaseAndTituloContainingIgnoreCase(
            String isbn, String titulo);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);
}