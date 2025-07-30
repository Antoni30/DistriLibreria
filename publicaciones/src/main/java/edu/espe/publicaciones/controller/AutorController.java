package edu.espe.publicaciones.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.espe.publicaciones.dto.AutorDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseDto crearAutor (@RequestBody AutorDto autorDto){

        return autorService.crearAutor(autorDto);
    }

    @GetMapping
    public List<ResponseDto> obtenerAutores(){
        return autorService.listarAutores();
    }
    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id){
        return autorService.autorPorId(id);
    }

    ///actulizar y eliminar

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto autorDto) {
        return autorService.actualizarAutor(id, autorDto);
    }
    @DeleteMapping("/{id}")
    public ResponseDto eliminarAutor(@PathVariable Long id) {
        return autorService.eliminarAutor(id);
    }

}
