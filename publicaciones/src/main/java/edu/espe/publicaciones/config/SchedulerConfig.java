package edu.espe.publicaciones.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import edu.espe.publicaciones.service.RelojProductor;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private RelojProductor relojProductor;

    @Scheduled(fixedRateString  = "${intervalo}")
    public void reportarHora() {
        relojProductor.enviarHora();

    }
}
