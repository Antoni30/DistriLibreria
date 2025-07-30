package edu.espe.publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.HoraClienteDTO;

import java.time.Instant;

@Slf4j
@Service
public class RelojProductor {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String nombreNodo= "ms-publicaciones";

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
