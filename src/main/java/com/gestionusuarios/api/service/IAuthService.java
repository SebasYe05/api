package com.gestionusuarios.api.service;


import com.gestionusuarios.api.dto.LoginRequestDTO;
import com.gestionusuarios.api.dto.LoginResponseDTO;
import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;

public interface IAuthService {

    RegisterResponseDTO registerUser(RegisterRequestDTO registerRequest);
    LoginResponseDTO loginUser(LoginRequestDTO loginRequest);
}
