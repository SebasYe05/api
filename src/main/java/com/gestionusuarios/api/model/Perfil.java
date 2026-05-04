package com.gestionusuarios.api.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document(collection = "perfiles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    @Id
    private ObjectId id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
}
