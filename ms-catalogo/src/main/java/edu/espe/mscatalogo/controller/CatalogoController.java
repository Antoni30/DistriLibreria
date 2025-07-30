package edu.espe.mscatalogo.controller;

import edu.espe.mscatalogo.entity.Catalogo;
import edu.espe.mscatalogo.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    @Autowired
    private CatalogoService catalogoService;

    @GetMapping
    public List<Catalogo> getAllCatalogo() {
        return catalogoService.getAllCatalogo();
    }
}
