package edu.espe.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDto {
    private String nombre;
    private String apellido;
    private String email;
    private String nacionalidad;
    private String institucion;
    private String orcid;

    public AutorDto(Long id, String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
    }
}
