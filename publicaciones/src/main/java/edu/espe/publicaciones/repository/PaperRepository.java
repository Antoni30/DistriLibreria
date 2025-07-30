package edu.espe.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.espe.publicaciones.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
}
