package edu.espe.notificaciones.config;

import edu.espe.notificaciones.service.RelojProductor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private RelojProductor relojProductor;

    @Scheduled(fixedRateString  = "1000")
    public void reportarHora() {
        relojProductor.enviarHora();
    }


}
