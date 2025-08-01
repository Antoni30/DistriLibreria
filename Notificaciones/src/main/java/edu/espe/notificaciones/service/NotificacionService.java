package edu.espe.notificaciones.service;

import edu.espe.notificaciones.entity.DTO.NotificacionesDTO;
import edu.espe.notificaciones.entity.Notificacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.notificaciones.respository.NotificacionesRepository;

import java.util.List;

@Slf4j
@Service
public class NotificacionService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    public List<Notificacion> listarNotificaciones(){
        log.info("Listando notificaciones");
        return notificacionesRepository.findAll();
    }

    public Notificacion guardarNotificacion(NotificacionesDTO notificacionesDTO){
        Notificacion n = new Notificacion();
        n.setMensaje(notificacionesDTO.getMensaje());
        n.setTipo(notificacionesDTO.getTipo());
        notificacionesRepository.save(n);
        return n;
    }

}
