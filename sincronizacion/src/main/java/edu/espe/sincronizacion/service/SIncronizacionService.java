package edu.espe.sincronizacion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.espe.sincronizacion.DTO.HoraClienteDTO;
import edu.espe.sincronizacion.DTO.HoraPromedioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SIncronizacionService {
    private Map<String, Long> tiempoClientes = new ConcurrentHashMap<>();
    private static final int INTERVALO_SEGUNDOS = 10;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void registrarTiempoCliente(HoraClienteDTO horaClienteDTO) {
        tiempoClientes.put(horaClienteDTO.getNombroServidor(), horaClienteDTO.getHoraEnviada());
    }

    public long sincronizarRelojes(){
        if(tiempoClientes.size()>=2){
            long ahora = Instant.now().toEpochMilli();
            long promedio = (ahora+tiempoClientes.values().stream().mapToLong(Long::longValue).sum())/(tiempoClientes.size()+1);
            tiempoClientes.clear();
            return promedio;
        }
        return 0;
    }

    public void  enviarAjuste(long promedio)
    {
        try {
            HoraPromedioDTO horaPromedioDTO = new HoraPromedioDTO(promedio);
            log.info("Enviando Promedio {}", promedio);
            String json = objectMapper.writeValueAsString(horaPromedioDTO);
            rabbitTemplate.convertAndSend("reloj.promedio", json);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
