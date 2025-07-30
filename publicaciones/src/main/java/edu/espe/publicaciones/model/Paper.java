package edu.espe.publicaciones.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Paper extends Publicacion {

    private String orcid;
    private LocalDateTime fechaPublicacion;
    private String revista;
    private String areaInvestigacion;

    @PrePersist
    public void prePersist() {
        this.fechaPublicacion = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name="id_autor")
    private Autor autor;
}
