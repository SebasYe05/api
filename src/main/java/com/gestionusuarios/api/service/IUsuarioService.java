package com.gestionusuarios.api.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.dto.UserResponseDTO;

public interface IUsuarioService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(ObjectId id);

    RegisterResponseDTO createUser(RegisterRequestDTO request);

    UserResponseDTO updateUser(ObjectId id, RegisterRequestDTO request);

    void deleteUser(ObjectId id);
}
