package edu.espe.publicaciones.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.PaperDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.model.Autor;
import edu.espe.publicaciones.model.Paper;
import edu.espe.publicaciones.repository.AutorRepository;
import edu.espe.publicaciones.repository.PaperRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaperService {
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private NotificacionProductor notificacionProductor;
    @Autowired
    private CatalogoProductor catalogoProductor;
    @Autowired
    private AutorRepository autorRepository;

    public ResponseDto createPaper(PaperDto paperDto) {
        Paper paper = new Paper();
        paper.setOrcid(paperDto.getOrcid());
        paper.setTitulo(paperDto.getTitulo());
        paper.setAnioPublicacion(paperDto.getAnioPublicacion());
        paper.setEditorial(paperDto.getEditorial());
        paper.setIsbn(paperDto.getIsbn());
        paper.setResumen(paperDto.getResumen());
        paper.setOrcid(paperDto.getOrcid());
        paper.setRevista(paperDto.getRevista());
        paper.setAreaInvestigacion(paperDto.getAreaInvestigacion());
        Autor autor = autorRepository.findById(paperDto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        paper.setAutor(autor);

        notificacionProductor.enviarNotificacion("Paper:"+paper.getTitulo(),"Paper");
        catalogoProductor.enviarCatologo(paper.getTitulo(),autor.getNombre(),"Paper");
        paperRepository.save(paper);
        return new ResponseDto("Paper creado exitosamente", paperDto);
    }

    public List<ResponseDto> getAllPapers() {
        return paperRepository.findAll().stream()
                .map(paper -> new ResponseDto("Paper:" + paper.getTitulo(), paper))
                        .collect(Collectors.toList());
    }

   public ResponseDto getPaper(int id) {
       Paper paper= paperRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe paper con id: " + id ));
       return new ResponseDto("Paper:"+paper.getTitulo(),paper);
   }

    public ResponseDto deletePaper(int id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe paper con id: " + id));

        paperRepository.delete(paper);

        return new ResponseDto("Paper eliminado exitosamente", null);
    }

    public ResponseDto updatePaper(int id, PaperDto paperDto) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe paper con id: " + id));

        mapDtoToEntity(paperDto, paper); // actualizamos el objeto existente

        paperRepository.save(paper);

        return new ResponseDto("Paper actualizado exitosamente", paper);
    }

    private void mapDtoToEntity(PaperDto dto, Paper entity) {
        entity.setTitulo(dto.getTitulo());
        entity.setAnioPublicacion(dto.getAnioPublicacion());
        entity.setEditorial(dto.getEditorial());
        entity.setIsbn(dto.getIsbn());
        entity.setResumen(dto.getResumen());
        entity.setOrcid(dto.getOrcid());
        entity.setRevista(dto.getRevista());
        entity.setAreaInvestigacion(dto.getAreaInvestigacion());
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        entity.setAutor(autor);
    }
}
