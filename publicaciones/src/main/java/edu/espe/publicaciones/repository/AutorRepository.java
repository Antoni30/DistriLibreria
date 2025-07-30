package edu.espe.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.espe.publicaciones.model.Autor;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    Optional<Autor> findById(Long id);
}
