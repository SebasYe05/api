package com.gestionusuarios.api.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String nameUser;
    private String pass;
}
