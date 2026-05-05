package com.gestionusuarios.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;
    private String nombreCompleto;
    private String bio;
    private String userName;
    private String role;
    private String mensaje;
}
