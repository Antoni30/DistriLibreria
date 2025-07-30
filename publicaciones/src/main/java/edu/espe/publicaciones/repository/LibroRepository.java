package edu.espe.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.espe.publicaciones.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
