package com.gestionusuarios.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private String id;
    private String nameUser;
    private String fullName;
    private String bio;
    private String rol;
}