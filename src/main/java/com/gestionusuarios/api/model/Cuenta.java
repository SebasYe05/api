package com.gestionusuarios.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "cuentas")
@Data
public class Cuenta {
    @Id
    private String id;

    @Indexed(unique = true)
    private String nombreUsuario;
    private String contrasena;
    
    private Rol rol;
}
