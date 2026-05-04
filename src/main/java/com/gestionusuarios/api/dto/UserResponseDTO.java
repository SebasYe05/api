package com.gestionusuarios.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private String id;
    private String nameUser;
    private String name;
    private String lastName;
    private String email;
    private String tel;
    private String rol;
}