package edu.espe.publicaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class PublicacionesApplication {
	public static void ajustarHora(long timestampMillis) {
		ZonedDateTime fechaHora = Instant.ofEpochMilli(timestampMillis)
				.atZone(ZoneId.systemDefault());

		// Formatos requeridos por el comando `date` y `time`
		String fecha = fechaHora.format(DateTimeFormatter.ofPattern("MM-dd-yy")); // MM-dd-yy
		String hora = fechaHora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));  // HH:mm:ss

		try {
			// Comando para cambiar la fecha
			String cmdFecha = "cmd.exe /c date " + fecha;
			// Comando para cambiar la hora
			String cmdHora = "cmd.exe /c time " + hora;

			// Ejecuta comando de fecha
			Process procesoFecha = Runtime.getRuntime().exec(cmdFecha);
			int resultadoFecha = procesoFecha.waitFor();

			// Ejecuta comando de hora
			Process procesoHora = Runtime.getRuntime().exec(cmdHora);
			int resultadoHora = procesoHora.waitFor();

			if (resultadoFecha == 0 && resultadoHora == 0) {
				System.out.println("✅ Hora del sistema actualizada a " + fechaHora);
			} else {
				System.err.println("❌ Error al actualizar hora/fecha. Ejecuta como administrador.");
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(PublicacionesApplication.class, args);
	}

}
