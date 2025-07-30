package edu.espe.mscatalogo.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.espe.mscatalogo.entity.DTO.CatalogoDTO;
import edu.espe.mscatalogo.service.CatalogoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogoListener {
    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "catalogo.cola")
    public void recibitMensajes(String mensaje) {
        try{
            CatalogoDTO dto = mapper.readValue(mensaje,CatalogoDTO.class);
            catalogoService.guardarCatalogo(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
