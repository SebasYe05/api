package com.gestionusuarios.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String nameUser;

    @NotBlank
    private String pass;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String tel;
}