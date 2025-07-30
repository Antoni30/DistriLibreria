package edu.espe.publicaciones.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="autor")
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false   )
    private String nombre;

    @Column(name="lastname", nullable = false)
    private String apellido;

    private String nacionalidad;
    private String institucion;

    @Column(name="email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name="orcid", nullable = false, unique = true, length = 50)
    private String orcid;

    @OneToMany(mappedBy = "autor" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paper> articulos;


}



