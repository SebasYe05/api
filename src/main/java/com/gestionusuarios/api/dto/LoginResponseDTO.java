package com.gestionusuarios.api.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private String id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String nombreUsuario;
    private String rol;
}
