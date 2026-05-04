package com.gestionusuarios.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    
    @NotBlank
    private String nameUser;

    @NotBlank
    private String pass;
}
