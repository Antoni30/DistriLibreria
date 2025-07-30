package edu.espe.publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.LibroDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.model.Autor;
import edu.espe.publicaciones.model.Libro;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import edu.espe.publicaciones.repository.AutorRepository;
import edu.espe.publicaciones.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProductor notificacionProductor;

    @Autowired
    private CatalogoProductor catalogoProductor;


    public ResponseDto crearLibro(LibroDto libroDto){
        Date fecha = new Date();
        Autor autor = autorRepository.findById(libroDto.getAutorId())
                .orElseThrow(()-> new RuntimeException("No existe autor con id: " + libroDto.getAutorId()));

        Libro libro = new Libro();
        libro.setAutor(autor);
        libro.setTitulo(libroDto.getTitulo());
        libro.setGenero(libroDto.getGenero());
        libro.setIsbn(libroDto.getIsbn());
        libro.setNumeroPaginas(libroDto.getNumeroPaginas());
        libro.setEditorial(libroDto.getEditorial());
        libro.setAnioPublicacion(libroDto.getAnioPublicacion());
        libro.setResumen(libroDto.getResumen());


        Libro libroGuardado = libroRepository.save(libro);
        LibroDto libroResponse = new LibroDto(
                libroGuardado.getId(),
                libroGuardado.getTitulo(),
                libroGuardado.getGenero(),
                libroGuardado.getIsbn(),
                libroGuardado.getNumeroPaginas(),
                libroGuardado.getEditorial(),
                libroGuardado.getAnioPublicacion(),
                libroGuardado.getResumen(),
                libroGuardado.getAutor().getId()


        );
        notificacionProductor.enviarNotificacion("Libro:"+libroGuardado.getTitulo()+ " fue creado","Libro");
        catalogoProductor.enviarCatologo(libroGuardado.getTitulo(),autor.getNombre(),"Libro");
        return new ResponseDto(
                "Libro registrado exitosamente", libroResponse);
    }

    //actualizar
    public ResponseDto actualizarLibro(Long id,LibroDto libroDto){
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe autor con id: " + id));
        Autor autor = (Autor) autorRepository.findById(libroDto.getAutorId())
                .orElseThrow(() -> new RuntimeException("No existe autor con id: " + libroDto.getAutorId()));

        libro.setAutor(autor);
        libro.setTitulo(libroDto.getTitulo());
        libro.setGenero(libroDto.getGenero());
        libro.setIsbn(libroDto.getIsbn());
        libro.setNumeroPaginas(libroDto.getNumeroPaginas());
        libro.setEditorial(libroDto.getEditorial());
        libro.setAnioPublicacion(libroDto.getAnioPublicacion());
        libro.setResumen(libroDto.getResumen());
        Libro actualizado = libroRepository.save(libro);
        return new ResponseDto("Libro actualizado exitosamente", actualizado);
    }
    //eliminar
    public ResponseDto eliminarLibro(Long id){
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe autor con id: " + id));
        libroRepository.delete(libro);
        return new ResponseDto("Libro eliminado exitosamente", null);
    }
    //listar
    public List<ResponseDto> listarLibros() {
        return libroRepository.findAll().stream()
                .map(libro -> new ResponseDto("Libro: " + libro.getTitulo(), libro))
                .collect(Collectors.toList());
    }

}
