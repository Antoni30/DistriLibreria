package edu.espe.publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.NotificacionesDTO;

@Service
public class NotificacionProductor {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public  void enviarNotificacion(String mensaje, String tipo) {
        try {
            NotificacionesDTO notificacionesDTO = new NotificacionesDTO(mensaje, tipo);
            String json = objectMapper.writeValueAsString(notificacionesDTO);
            rabbitTemplate.convertAndSend("notificaciones.cola", json);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
