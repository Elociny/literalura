package com.elociny.literalura.repository;

import com.elociny.literalura.model.Autor;
import com.elociny.literalura.model.Idiomas;
import com.elociny.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nomeAutor);

    @Query("SELECT l FROM Livro l")
    List<Livro> listarLivroRegistrados();

    @Query("SELECT a FROM Autor a")
    List<Autor> listarAutoresRegitrados();

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoDeNascimento AND a.anoDeFalecimento")
    List<Autor> listarAutoresVivosPeloAno(int ano);

    @Query("select l from Livro l where l.idiomas = :idioma")
    List<Livro> listarLivrosPorIdioma(Idiomas idioma);

    @Query("Select l from Livro l order by l.downloads desc limit 10")
    List<Livro> obterTop10Baixados();
}