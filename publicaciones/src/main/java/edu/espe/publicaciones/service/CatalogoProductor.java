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

    /**
     * Envía un mensaje con los datos del catálogo a la cola de RabbitMQ.
     * Este método es utilizado para comunicar la creación o actualización de un catálogo
     * a otros microservicios de manera asíncrona.
     *
     * @param titulo Título del catálogo
     * @param autor  Autor del catálogo
     * @param tipo   Tipo de catálogo
     */
    public  void enviarCatologo(String titulo, String autor,String tipo) {
        try {
            // Se crea un objeto DTO con los datos recibidos
            CatalogoDTO catalogoDTO = new CatalogoDTO(tipo,titulo, autor);
            // Se convierte el objeto a formato JSON
            String json = objectMapper.writeValueAsString(catalogoDTO);
            // Se envía el mensaje JSON a la cola 'catalogo.cola' de RabbitMQ
            rabbitTemplate.convertAndSend("catalogo.cola", json);
        }catch (Exception e) {
            // En caso de error, se imprime la traza para depuración
            e.printStackTrace();
        }
    }
}
