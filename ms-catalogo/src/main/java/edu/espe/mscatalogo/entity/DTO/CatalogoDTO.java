package edu.espe.mscatalogo.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDTO {
    private String tipo;
    private String titulo;
    private String Autor;
}
