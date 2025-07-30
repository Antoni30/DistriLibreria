package edu.espe.mscatalogo.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoraClienteDTO {
    private String nombroServidor;
    private long horaEnviada;
}
