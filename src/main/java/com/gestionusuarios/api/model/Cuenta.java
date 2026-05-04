package com.gestionusuarios.api.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;



@Document(collection = "cuentas")
@Data
@Builder
public class Cuenta {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String nombreUsuario;
    private String contrasena;
    
    private Rol rol;
}
