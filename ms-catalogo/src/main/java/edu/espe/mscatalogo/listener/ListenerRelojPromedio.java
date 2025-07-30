package edu.espe.mscatalogo.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.espe.mscatalogo.entity.DTO.HoraPromedioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class ListenerRelojPromedio {
    ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "reloj.promedio")
    public void recibirHora(String mensajePromedio) {
        try {
            HoraPromedioDTO horaPromedioDTO = mapper.readValue(mensajePromedio, HoraPromedioDTO.class);
            long timestampRecibido = horaPromedioDTO.getHoraEnviada();

            Instant instanteRecibido = Instant.ofEpochMilli(timestampRecibido);
            Instant instanteActual = Instant.now();

            Duration diferencia = Duration.between(instanteRecibido, instanteActual);
            long diffMillis = diferencia.toMillis();

            if (diffMillis > 0) {
                log.info("El reloj del servidor está adelantado por {} ms", diffMillis);
            } else if (diffMillis < 0) {
                log.info("El reloj del servidor está atrasado por {} ms", Math.abs(diffMillis));
            } else {
                log.info("El reloj del servidor está sincronizado con la hora promedio");
            }

        } catch (Exception e) {
            log.error("Error procesando hora promedio", e);
        }
    }
}
