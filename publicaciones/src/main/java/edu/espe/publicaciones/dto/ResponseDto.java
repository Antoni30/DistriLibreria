package edu.espe.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ResponseDto {
    private String mensaje;
    private Object dato;
}
