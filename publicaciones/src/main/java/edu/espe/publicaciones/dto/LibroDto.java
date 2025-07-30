package edu.espe.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {

    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String genero;
    private int numeroPaginas;
    private  Long autorId;
    private String resumen;



    public LibroDto(Long id, String titulo, String genero, String isbn, int numeroPaginas, String editorial, int anioPublicacion, String resumen, Long autorId) {
        this.titulo = titulo;
        this.genero = genero;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.resumen = resumen;
        this.autorId = autorId;
    }
}
