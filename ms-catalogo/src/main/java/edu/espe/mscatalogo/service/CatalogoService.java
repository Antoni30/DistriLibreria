package edu.espe.mscatalogo.service;

import edu.espe.mscatalogo.entity.Catalogo;
import edu.espe.mscatalogo.entity.DTO.CatalogoDTO;
import edu.espe.mscatalogo.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoRepository catalogoRepository;

    public List<Catalogo> getAllCatalogo() {
        return catalogoRepository.findAll();
    }
    public Catalogo guardarCatalogo(CatalogoDTO catalogoDTO) {
        Catalogo catalogo = new Catalogo();
        catalogo.setAutor(catalogoDTO.getAutor());
        catalogo.setTipo(catalogoDTO.getTipo());
        catalogo.setTitulo(catalogoDTO.getTitulo());
        catalogoRepository.save(catalogo);
        return catalogo;
    }
}
