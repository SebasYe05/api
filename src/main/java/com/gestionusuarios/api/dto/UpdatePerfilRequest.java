package com.gestionusuarios.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePerfilRequest {

    @NotBlank
    private String nameUser;

    @NotBlank
    private String fullName;

    @NotBlank
    private String bio;

    @NotBlank
    private String pass;

    @NotBlank
    private String confirmPassword;
}
