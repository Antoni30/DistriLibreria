package edu.espe.publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.espe.publicaciones.dto.LibroDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.service.LibroService;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseDto createLibro(@RequestBody LibroDto libroDto){
        return libroService.crearLibro(libroDto);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libroDto) {
        return libroService.actualizarLibro(id, libroDto);
    }
    @DeleteMapping("/{id}")
    public ResponseDto eliminarLibro(@PathVariable Long id) {
        return libroService.eliminarLibro(id);
    }
    @GetMapping
    public List<ResponseDto> listarLibros() {
        return libroService.listarLibros();
    }
}
