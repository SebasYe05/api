package com.gestionusuarios.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String mensaje;
    private LocalDateTime fechaError;
    private int codigoStatus;
}
