package edu.espe.notificaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.espe.notificaciones.entity.DTO.HoraClienteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class RelojProductor {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String nombreNodo= "ms-notificaciones";

    public void enviarHora(){
        try{
            HoraClienteDTO dto = new HoraClienteDTO(nombreNodo, Instant.now().toEpochMilli());
            String json = objectMapper.writeValueAsString(dto);

            amqpTemplate.convertAndSend("reloj.solicitud",json);
        }catch (Exception e){
            log.error("Error al enviar el Hora del productor");
        }
    }
}
