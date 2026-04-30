package com.gestionusuarios.api.mapper;

import com.gestionusuarios.api.dto.LoginResponseDTO;
import com.gestionusuarios.api.model.Cuenta;
import com.gestionusuarios.api.model.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
   
    @Mapping(target = "id", source = "perfil.id")
    @Mapping(target = "nombres", source = "perfil.nombres")
    @Mapping(target = "apellidos", source = "perfil.apellidos")
    @Mapping(target = "email", source = "perfil.email")
    @Mapping(target = "telefono", source = "perfil.telefono")
    @Mapping(target = "nombreUsuario", source = "cuenta.nombreUsuario")
    @Mapping(target = "rol", source = "cuenta.rol")
    @Mapping(target = "token", ignore = true) 
    LoginResponseDTO toDto(Perfil perfil, Cuenta cuenta);
}
