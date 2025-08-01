package edu.espe.notificaciones.controller;

import edu.espe.notificaciones.entity.Notificacion;
import edu.espe.notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<Notificacion> getNotificaciones(){
        return  notificacionService.listarNotificaciones();
    }
}
