package com.gestionusuarios.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;
    private String names;
    private String lastNames;
    private String email;
    private String tel;
    private String userName;
    private String role;
    private String mensaje;
}
