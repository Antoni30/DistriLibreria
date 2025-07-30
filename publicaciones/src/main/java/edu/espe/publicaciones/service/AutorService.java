package edu.espe.publicaciones.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.AutorDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.model.Autor;
import edu.espe.publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProductor notificacionProductor;

    public ResponseDto crearAutor (AutorDto autorDto){

        Autor autor = new Autor();
        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        autor.setEmail(autorDto.getEmail());
        autor.setNacionalidad(autorDto.getNacionalidad());
        autor.setInstitucion(autorDto.getInstitucion());
        autor.setOrcid(autorDto.getOrcid());

        Autor guardado =  autorRepository.save(autor);
        notificacionProductor.enviarNotificacion("Autor:"+autor.getNombre()+ " fue creado","Autor");
        return new ResponseDto("Autor registrado exitosamente", guardado);
        //como se mnaneja errores, si es null o no existe el autor
    }

    public List<ResponseDto> listarAutores(){
        return autorRepository.findAll().stream()
                .map(autor -> new ResponseDto("Autor:" + autor.getApellido(), autor))
                .collect(Collectors.toList());
    }

    public ResponseDto autorPorId(Long id){

        Autor autor = autorRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe autor con id: " + id ));
        return new ResponseDto("Autor con id:" + autor.getId(), autor);

    }


    public ResponseDto eliminarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe autor con id: " + id));

        autorRepository.delete(autor);

        return new ResponseDto("Autor eliminado exitosamente", null);
    }

    public ResponseDto actualizarAutor(Long id, AutorDto autorDto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe autor con id: " + id));

        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        autor.setEmail(autorDto.getEmail());
        autor.setNacionalidad(autorDto.getNacionalidad());
        autor.setInstitucion(autorDto.getInstitucion());
        autor.setOrcid(autorDto.getOrcid());

        Autor actualizado = autorRepository.save(autor);
        return new ResponseDto("Autor actualizado exitosamente", actualizado);
    }

}
