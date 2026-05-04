package com.gestionusuarios.api.mapper;

import com.gestionusuarios.api.dto.LoginResponseDTO;
import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.UserResponseDTO;
import com.gestionusuarios.api.model.Cuenta;
import com.gestionusuarios.api.model.Perfil;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "names", source = "perfil.nombres")
    @Mapping(target = "lastNames", source = "perfil.apellidos")
    @Mapping(target = "email", source = "perfil.correo")
    @Mapping(target = "tel", source = "perfil.telefono")
    @Mapping(target = "userName", source = "cuenta.nombreUsuario")
    @Mapping(target = "role", source = "cuenta.rol")
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "mensaje", ignore = true)
    LoginResponseDTO toDto(Perfil perfil, Cuenta cuenta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombreUsuario", source = "registerRequest.nameUser")
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Cuenta toCuenta(RegisterRequestDTO registerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombres", source = "registerRequest.name")
    @Mapping(target = "apellidos", source = "registerRequest.lastName")
    @Mapping(target = "correo", source = "registerRequest.email")
    @Mapping(target = "telefono", source = "registerRequest.tel")
    Perfil toPerfil(RegisterRequestDTO registerRequest);

    @Mapping(target = "id", expression = "java(perfil.getId().toString())")
    @Mapping(target = "nameUser", source = "cuenta.nombreUsuario")
    @Mapping(target = "name", source = "perfil.nombres")
    @Mapping(target = "lastName", source = "perfil.apellidos")
    @Mapping(target = "email", source = "perfil.correo")
    @Mapping(target = "tel", source = "perfil.telefono")
    @Mapping(target = "rol", expression = "java(cuenta.getRol() != null ? cuenta.getRol().name() : \"\")")
    UserResponseDTO toUserResponseDTO(Perfil perfil, Cuenta cuenta);

    default String map(ObjectId value) {
        return value.toString();
    }

}
