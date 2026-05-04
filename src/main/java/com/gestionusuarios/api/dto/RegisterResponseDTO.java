package com.gestionusuarios.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {
    private LocalDateTime fechaRegistro;
    private String mensaje;
}
