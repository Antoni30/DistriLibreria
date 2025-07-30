package edu.espe.notificaciones.respository;

import edu.espe.notificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionesRepository extends JpaRepository<Notificacion, Long> {
}
