package com.gestionusuarios.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "perfiles")
@Data
public class Perfil {
    @Id
    private String id;
    private String nombres;
    private String apellidos;

    private String email;
    private String telefono;
    private String cuentaId;
}
