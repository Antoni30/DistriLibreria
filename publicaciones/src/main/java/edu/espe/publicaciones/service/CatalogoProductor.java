package edu.espe.publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.espe.publicaciones.dto.CatalogoDTO;

@Service
public class CatalogoProductor {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public  void enviarCatologo(String titulo, String autor,String tipo) {
        try {
            CatalogoDTO catalogoDTO = new CatalogoDTO(tipo,titulo, autor);
            String json = objectMapper.writeValueAsString(catalogoDTO);
            rabbitTemplate.convertAndSend("catalogo.cola", json);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
